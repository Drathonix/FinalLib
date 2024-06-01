package com.vicious.finallib.common.nbt;

import com.vicious.finallib.FinalLib;
import com.vicious.finallib.common.nbt.access.INBTAccessor;
import com.vicious.finallib.common.nbt.access.WorldAccessor;
import com.vicious.finallib.common.nbt.reflect.BadAnnotationException;
import com.vicious.finallib.common.nbt.reflect.ExposedNBTField;
import com.vicious.finallib.common.nbt.reflect.Manifest;
import com.vicious.finallib.common.nbt.reflect.ReflectionCache;
import com.vicious.finallib.common.util.SerializationHandler;
import net.minecraft.nbt.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * A secure NBT data interface.
 */
public interface INBTStorable {
    default CompoundTag save(){
        return read(WorldAccessor.INSTANCE);
    }
    default void load(CompoundTag tag){
        write(tag,WorldAccessor.INSTANCE);
    }

    default void write(CompoundTag tag, INBTAccessor accessor){
        Manifest manifest = ReflectionCache.getManifest(this);
        for (String key : tag.getAllKeys()) {
            ExposedNBTField field = manifest.getField(key);
            if(field.canWrite(accessor)) {
                try {
                    field.set(this, deserialize(field.get(this), tag.get(key), field.getType(), accessor, field.getTyping()));
                } catch (IllegalArgumentException t) {
                    //TODO: improve admin warnings
                    FinalLib.logger.warn(t.getMessage());
                }
            }
            else{
                //TODO: improve admin warnings
                FinalLib.logger.warn(accessor.getName() + " attempted to write a value to NBT key: " + key + " without write permission.");
            }
        }
    }

    default CompoundTag read(INBTAccessor accessor){
        Manifest manifest = ReflectionCache.getManifest(this);
        CompoundTag out = new CompoundTag();
        manifest.getFields().forEach((key,field)->{
            if(field.canRead(accessor)) {
                out.put(key, serialize(field.get(this), accessor));
            }
        });
        return out;
    }

    default Tag serialize(Object value, INBTAccessor accessor){
        if(value instanceof Tag){
            return (Tag) value;
        }
        if(value instanceof Byte){
            return ByteTag.valueOf((Byte) value);
        }
        else if(value instanceof Short){
            return ShortTag.valueOf((Short) value);
        }
        else if(value instanceof Integer){
            return IntTag.valueOf((Integer) value);
        }
        else if(value instanceof Long){
            return LongTag.valueOf((Long) value);
        }
        else if(value instanceof Float){
            return FloatTag.valueOf((Float) value);
        }
        else if(value instanceof Double){
            return DoubleTag.valueOf((Double) value);
        }
        else if(value instanceof String){
            return StringTag.valueOf((String) value);
        }
        else if(value instanceof UUID){
            return NbtUtils.createUUID((UUID)value);
        }
        else if(value instanceof byte[]){
            return new ByteArrayTag((byte[]) value);
        }
        else if(value instanceof int[]){
            return new IntArrayTag((int[]) value);
        }
        else if(value instanceof long[]){
            return new LongArrayTag((long[]) value);
        }
        else if(value instanceof INBTStorable storable){
            return storable.read(accessor);
        }
        else if(value instanceof Map map){
            CompoundTag result = new CompoundTag();
            map.forEach((k,v)->{
                result.put(SerializationHandler.serialize(k),serialize(v,accessor));
            });
            return result;
        }
        else if(value instanceof Collection list){
            ListTag result = new ListTag();
            list.forEach(v->{
                result.add(serialize(v,accessor));
            });
            return result;
        }
        throw new BadAnnotationException(value.getClass() + " cannot be serialized into NBT.");
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    default Object deserialize(Object current, Tag nbt, Class<?> targetType, INBTAccessor accessor, Class<?>... typing){
        if(nbt.getClass() == targetType){
            return nbt;
        }
        else if(nbt instanceof CompoundTag ct){
            current = SerializationHandler.initialize(targetType);
            if(current instanceof INBTStorable c){
                c.write(ct,accessor);
                return c;
            }
            if(current instanceof Map m){
                m.clear();
                if (typing.length > 1) {
                    Class<?> keyType = typing[0];
                    Class<?> valType = typing[1];
                    Object defVal = SerializationHandler.initialize(valType);
                    for (String key : ct.getAllKeys()) {
                        m.put(SerializationHandler.deserialize(key,keyType),deserialize(defVal, ct.get(key), valType, accessor, typing.length > 2 ? Arrays.copyOfRange(typing, 2, typing.length) : new Class<?>[0]));
                    }
                }
                else{
                    throw new BadAnnotationException("A field in class " + getClass() + " is lacks appropriate @Typing for a map. Please assign.");
                }
                return m;
            }
            else{
                throw new IllegalArgumentException("Target type cannot be loaded from a CompoundTag.");
            }
        }
        if(nbt instanceof StringTag stringTag){
            return stringTag.getAsString();
        }
        else if(nbt instanceof NumericTag numericTag){
            if(targetType == boolean.class || targetType == Boolean.class){
                return ((NumericTag) nbt).getAsByte() == 1;
            }
            else if(nbt instanceof ByteTag){
                return numericTag.getAsByte();
            }
            else if(nbt instanceof ShortTag){
                return numericTag.getAsShort();
            }
            else if(nbt instanceof IntTag){
                return numericTag.getAsInt();
            }
            else if(nbt instanceof LongTag){
                return numericTag.getAsLong();
            }
            else if(nbt instanceof FloatTag){
                return numericTag.getAsFloat();
            }
            else if(nbt instanceof DoubleTag){
                return numericTag.getAsDouble();
            }
        }
        else if(nbt instanceof ByteArrayTag bat){
            return bat.getAsByteArray();
        }
        else if(nbt instanceof IntArrayTag iat){
            if(targetType == UUID.class){
                return NbtUtils.loadUUID(iat);
            }
            return iat.getAsIntArray();
        }
        else if(nbt instanceof LongArrayTag lat){
            return lat.getAsLongArray();
        }
        else if(nbt instanceof ListTag list){
            if(current instanceof Collection collection){
                collection.clear();
                for (Tag tag : list) {
                    if (typing.length > 0) {
                        Class<?> type = typing[0];
                        Object defVal = SerializationHandler.initialize(type);
                        collection.add(deserialize(defVal, tag, type, accessor, typing.length > 1 ? Arrays.copyOfRange(typing, 1, typing.length) : new Class<?>[0]));
                    }
                    else{
                        throw new BadAnnotationException("A field in class " + getClass() + " is lacks appropriate @Typing for a collection. Please assign.");
                    }
                }
            }
        }
        throw new BadAnnotationException("Support for nbt of type " + nbt.getClass().getName() + " is not yet supported. Either submit a pull request or an issue.");
    }
}
