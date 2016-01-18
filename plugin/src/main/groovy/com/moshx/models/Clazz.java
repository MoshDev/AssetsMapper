package com.moshx.models;

import java.util.ArrayList;
import java.util.List;

public class Clazz {

    public String name;
    public List<Field> fields = new ArrayList<Field>();
    public List<Clazz> subClazzes = new ArrayList<Clazz>();

    public class Field {
        public String name, value;
    }

}
