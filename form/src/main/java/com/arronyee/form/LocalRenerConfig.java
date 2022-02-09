package com.arronyee.form;

public class LocalRenerConfig {

    private boolean isAdd;
    private RenderCallback renderCallback;

    public interface RenderCallback{
        void onReady();
    }

    private LocalRenerConfig(boolean isAdd,RenderCallback renderCallback){
        this.isAdd = isAdd;
        this.renderCallback = renderCallback;

    }

    public static class Build{
        private boolean isAdd;
        private RenderCallback renderCallback;

        public Build isAdd(boolean isAdd){
            this.isAdd= isAdd;
            return this;
        }

        public Build renderCallback(RenderCallback renderCallback){
            this.renderCallback= renderCallback;
            return this;
        }

        public LocalRenerConfig build(){
            return new LocalRenerConfig(isAdd,renderCallback);
        }

    }

    public boolean isAdd() {
        return isAdd;
    }

    public RenderCallback getRenderCallback() {
        return renderCallback;
    }
}
