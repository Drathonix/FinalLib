package com.vicious.finallib.common.nbt.access;

public enum Access {
    NONE{
        @Override
        public boolean canRead() {
            return true;
        }
    },
    READ,
    WRITE{
        @Override
        public boolean canWrite() {
            return true;
        }

    };

    public boolean canRead(){
        return true;
    }

    public boolean canWrite(){
        return false;
    }

    public boolean supercedes(Access access) {
        return this.ordinal() >= access.ordinal();
    }
}
