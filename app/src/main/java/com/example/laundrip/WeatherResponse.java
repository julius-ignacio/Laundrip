package com.example.laundrip;

public class WeatherResponse {
    private Current current;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public class Current {
        private String temp_c;
        private Condition condition;

        public String getTempC() {
            return temp_c;
        }

        public void setTempC(String temp_c) {
            this.temp_c = temp_c;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public class Condition {
            private String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}