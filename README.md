# Form

[![](https://jitpack.io/v/ArronYee/Form.svg)](https://jitpack.io/#ArronYee/Form)

# what's the "Form" in Android ?
Inspired by Vue or others web-front frame,this "Form" makes field create,update,validation,submit with just one json and easy.

You just need get a json(whatever it is from) to generate a form-submit ,customize your validation,it also supported you fill your field-value into form.

When your business is very complex,and you hava to write a lot of XML layout or Activity,this is a good chooice for you.

In this folder repository contains:

1. [/app](https://github.com/ArronYee/Form/tree/main/app) a sample example for Form,include add and modify.
2. [/form](https://github.com/ArronYee/Form/tree/main/form) the libs code.

## Table of Contents

- [Partial code description](#Partial code description)
- [Install](#install)
- [Related Mentality](#Related Mentality)
- [To be improved](#To be improved)

## Partial code description

```sh
{
    "domains":{
        "personType":[
            {"value":"1","text":"人口1"},
            {"value":"2","text":"人口2"}
        ],
	"csType":[
            {"value":"1","text":"测试类型1"},
	    {"value":"2","text":"测试类型2"},
            {"value":"3","text":"测试类型3"},
            {"value":"4","text":"测试类型4"},
            {"value":"5","text":"测试类型5"}
        ]
    },
    "formdesc":{
        "main":{
            "fields":[
                {"label":"文字","name":"name","type":"string:0/12","view":0,"required":1},
                {"label":"单选","name":"personType","type":"select:domain/personType","view":0,"required":1},
		{"label":"多选","name":"csType","type":"select_multi:domain/csType","view":0,"required":0},
                {"label":"是否","name":"isYes","type":"bool:0/1","view":0,"required":0},
                {"label":"日期","name":"date","type":"date:yyyy-MM-dd","view":0,"required":0},
		{"label":"备注","name":"remark","type":"memo:0/200","view":0,"required":0}
            ]
        }
    }
}
```

This show you how to generate a form include text-input,single or multi selector,boolean,date,or multi-line. the "domains" array is "select:domain/xxxxx" the select item.

```sh
 isr = new InputStreamReader(addDataActivity.getAssets().open("cxry.json"), "UTF-8");
    br = new BufferedReader(isr);
    while ((temp = br.readLine()) != null) {
	sb.append(temp);
    }
serverAddField = new Gson().fromJson(sb.toString(), ServerAddField.class);
    
localRender.bind(addDataActivity, (LinearLayout) addDataActivity.getContainer(),addDataActivity.getRenderConfig());
localRender.setData(sb.toString());

```
We need bind this json data into LocalRender.

```sh
    public void submit() {
        if (localRender.getServerDataProvider().validateAll()) {
            String result = localRender.getServerDataProvider().getSubmitStr();
            Toast.makeText(addDataActivity,result,Toast.LENGTH_LONG).show();
            Log.d("yeying","submit result --- "+result);
        }
    }

```

When we want get result,validateAll() and getSubmitStr(), you can get your result value,submit it.



```sh
  JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name","測試");
            jsonObject.put("personType","1");
            jsonObject.put("csType","1,2");
            jsonObject.put("isYes","0");
            jsonObject.put("date","2020-01-01");
            jsonObject.put("remark","測試備注");
        } catch (JSONException e) {
            e.printStackTrace();
        }
   addDataActivityPresenter.fillField(jsonObject);

```

your also can get your pre-data into form.and then form has the default value.

## Install

In your project build.gradle:

```sh
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

In your needed module
```sh
	dependencies {
		  implementation 'com.github.ArronYee:Form:0.0.3'
	}
```

## Related Mentality

This Sample use mvp frame,but in the "form", is more likely mvvm,

i use
```sh
 tv_last.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                memoString.setValue(s.toString());
            }
        });
```


```sh
 DataParser dataParser = serverDataProvider.updateItemValue(name, value);
 
 //--//
 
 localRender.updateItemView(name, showText, value, dataParser.getView() == 1);
 
 
```

Ensure immediate response between them。

## To be improved
In my own business project,it also support "Cordition","ExtraFile",but it is inconvenient to package into Library,it has map or upload file need to be done,so let me think and it will be improved in the future。


