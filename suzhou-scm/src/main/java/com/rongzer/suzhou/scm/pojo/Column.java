package com.rongzer.suzhou.scm.pojo;


import lombok.Getter;
import lombok.Setter;

/**
 * @author pshe
 */
@Getter
@Setter
public class Column {

        public Column(String name, String des) {
                this.name = name;
                this.des = des;
        }

        /**
         * 列名
         */
        private String name;
        /**
         * 列描述
         */
        private String des;
}