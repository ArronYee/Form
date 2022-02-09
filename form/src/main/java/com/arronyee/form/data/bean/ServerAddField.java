package com.arronyee.form.data.bean;


import java.util.List;

/**
 * Created by yeying on 2018/9/11.
 */

public class ServerAddField {

    /**
     * domains : {"personType":[{"value":"1","text":"户籍人口"},{"value":"2","text":"流动人口"}],"riskAssessment":[{"value":"01","text":"重点帮教人员"},{"value":"02","text":"一般帮教人员"}],"goeson":[{"value":"01","text":"基层政府接回"},{"value":"02","text":"监所送回原籍"},{"value":"03","text":"司法所接回"},{"value":"04","text":"公安机关落实管控措施"},{"value":"05","text":"基层组织接回"},{"value":"06","text":"人员白返"},{"value":"99","text":"其他衔接情况"}],"keycontrols":[{"value":"01","text":"是"},{"value":"02","text":"否"}],"isSet":[{"value":"01","text":"是"},{"value":"02","text":"否"}],"recidivism":[{"value":"01","text":"是"},{"value":"02","text":"否"}]}
     * formdesc : {"main":{"label":"人员信息","name":"main","primarykey":"id","fields":[{"label":"公民身份证号码","name":"cardNum","type":"string:15/18","view":0,"required":1},{"label":"姓名","name":"name","type":"cordition","view":0,"required":1},{"label":"人口类型","name":"personType","type":"select:domain/personType","view":0,"required":1},{"label":"所属网格","name":"gId","type":"select:pagetree/grid","view":0,"required":1},{"label":"联系方式","name":"phone","type":"string:0/11","view":0,"required":0},{"label":"现住门（楼）详址","name":"rAddr","type":"string:0/200","view":0,"required":1},{"label":"现住地","name":"residence","type":"string:0/200","view":0,"required":0},{"label":"危险评估类型","name":"riskAssessment","type":"select:domain/riskAssessment","view":0,"required":0},{"label":"释放日期","name":"releaseDate","type":"date:yyyy-MM-dd","view":0,"required":1},{"label":"原职业","name":"occupation","type":"string:0/50","view":0,"required":0},{"label":"衔接日期","name":"joinDate","type":"date:yyyy-MM-dd","view":0,"required":1},{"label":"衔接情况","name":"goeson","type":"string:0/50","view":0,"required":0},{"label":"犯罪行为","name":"criminal","type":"string:0/50","view":0,"required":0},{"label":"是否重点管控","name":"keycontrols","type":"select:domain/keycontrols","view":0,"required":0},{"label":"是否安置","name":"isSet","type":"select:domain/building_structure","view":0,"required":0},{"label":"是否累犯","name":"recidivism","type":"date:yyyy-MM-dd","view":0,"required":0},{"label":"帮教情况","name":"eduSituation","type":"string:0/200","view":0,"required":0},{"label":"备注","name":"remark","type":"string:0/200","view":0,"required":0}]},"extends":[]}
     */

    private Object domains;
    private FormdescBean formdesc;

    public class Element {
        private String value;
        private String text;

        public Element(){}

        public Element(String value, String text) {
            this.value = value;
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public Object getDomains() {
        return domains;
    }

    public void setDomains(Object domains) {
        this.domains = domains;
    }

    public FormdescBean getFormdesc() {
        return formdesc;
    }

    public void setFormdesc(FormdescBean formdesc) {
        this.formdesc = formdesc;
    }

    public static class FormdescBean {
        /**
         * main : {"label":"人员信息","name":"main","primarykey":"id","fields":[{"label":"公民身份证号码","name":"cardNum","type":"string:15/18","view":0,"required":1},{"label":"姓名","name":"name","type":"cordition","view":0,"required":1},{"label":"人口类型","name":"personType","type":"select:domain/personType","view":0,"required":1},{"label":"所属网格","name":"gId","type":"select:pagetree/grid","view":0,"required":1},{"label":"联系方式","name":"phone","type":"string:0/11","view":0,"required":0},{"label":"现住门（楼）详址","name":"rAddr","type":"string:0/200","view":0,"required":1},{"label":"现住地","name":"residence","type":"string:0/200","view":0,"required":0},{"label":"危险评估类型","name":"riskAssessment","type":"select:domain/riskAssessment","view":0,"required":0},{"label":"释放日期","name":"releaseDate","type":"date:yyyy-MM-dd","view":0,"required":1},{"label":"原职业","name":"occupation","type":"string:0/50","view":0,"required":0},{"label":"衔接日期","name":"joinDate","type":"date:yyyy-MM-dd","view":0,"required":1},{"label":"衔接情况","name":"goeson","type":"string:0/50","view":0,"required":0},{"label":"犯罪行为","name":"criminal","type":"string:0/50","view":0,"required":0},{"label":"是否重点管控","name":"keycontrols","type":"select:domain/keycontrols","view":0,"required":0},{"label":"是否安置","name":"isSet","type":"select:domain/building_structure","view":0,"required":0},{"label":"是否累犯","name":"recidivism","type":"date:yyyy-MM-dd","view":0,"required":0},{"label":"帮教情况","name":"eduSituation","type":"string:0/200","view":0,"required":0},{"label":"备注","name":"remark","type":"string:0/200","view":0,"required":0}]}
         * extends : []
         */

        private MainBean main;
        private MainBean extend;

        public MainBean getMain() {
            return main;
        }

        public void setMain(MainBean main) {
            this.main = main;
        }

        public MainBean getExtendsX() {
            return extend;
        }

        public void setExtendsX(MainBean extendsX) {
            this.extend = extendsX;
        }

        public static class MainBean {
            /**
             * label : 人员信息
             * name : main
             * primarykey : id
             * fields : [{"label":"公民身份证号码","name":"cardNum","type":"string:15/18","view":0,"required":1},{"label":"姓名","name":"name","type":"cordition","view":0,"required":1},{"label":"人口类型","name":"personType","type":"select:domain/personType","view":0,"required":1},{"label":"所属网格","name":"gId","type":"select:pagetree/grid","view":0,"required":1},{"label":"联系方式","name":"phone","type":"string:0/11","view":0,"required":0},{"label":"现住门（楼）详址","name":"rAddr","type":"string:0/200","view":0,"required":1},{"label":"现住地","name":"residence","type":"string:0/200","view":0,"required":0},{"label":"危险评估类型","name":"riskAssessment","type":"select:domain/riskAssessment","view":0,"required":0},{"label":"释放日期","name":"releaseDate","type":"date:yyyy-MM-dd","view":0,"required":1},{"label":"原职业","name":"occupation","type":"string:0/50","view":0,"required":0},{"label":"衔接日期","name":"joinDate","type":"date:yyyy-MM-dd","view":0,"required":1},{"label":"衔接情况","name":"goeson","type":"string:0/50","view":0,"required":0},{"label":"犯罪行为","name":"criminal","type":"string:0/50","view":0,"required":0},{"label":"是否重点管控","name":"keycontrols","type":"select:domain/keycontrols","view":0,"required":0},{"label":"是否安置","name":"isSet","type":"select:domain/building_structure","view":0,"required":0},{"label":"是否累犯","name":"recidivism","type":"date:yyyy-MM-dd","view":0,"required":0},{"label":"帮教情况","name":"eduSituation","type":"string:0/200","view":0,"required":0},{"label":"备注","name":"remark","type":"string:0/200","view":0,"required":0}]
             */

            private String label;
            private String name;
            private String primarykey;
            private List<FieldsBean> fields;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrimarykey() {
                return primarykey;
            }

            public void setPrimarykey(String primarykey) {
                this.primarykey = primarykey;
            }

            public List<FieldsBean> getFields() {
                return fields;
            }

            public void setFields(List<FieldsBean> fields) {
                this.fields = fields;
            }

        }

        public static class FieldsBean {
            /**
             * label : 公民身份证号码
             * name : cardNum
             * type : string:15/18
             * view : 0
             * required : 1
             */

            private String label;
            private String name;
            private String type;
            private int view;
            private int required;
            private String defaultValue;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getView() {
                return view;
            }

            public void setView(int view) {
                this.view = view;
            }

            public int getRequired() {
                return required;
            }

            public void setRequired(int required) {
                this.required = required;
            }

            public String getDefalutValue() {
                return defaultValue;
            }

            public void setDefalutValue(String defalutValue) {
                this.defaultValue = defalutValue;
            }
        }
    }
}
