package com.arronyee.form;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.arronyee.form.data.parser.Cordition;
import com.arronyee.form.data.parser.MemoString;
import com.arronyee.form.data.ServerDataProvider;
import com.arronyee.form.data.parser.BooleanSelect;
import com.arronyee.form.data.parser.DataParser;
import com.arronyee.form.data.parser.DateSelect;
import com.arronyee.form.data.parser.MultiSelect;
import com.arronyee.form.data.parser.Select;
import com.arronyee.form.data.parser.TextInteger;
import com.arronyee.form.data.parser.TextString;
import com.arronyee.form.utils.AnimationUtils;
import com.arronyee.form.view.widget.MultiSelectPop;
import com.arronyee.form.view.widget.MyBaseAdapter;
import com.arronyee.form.view.widget.ViewHolder;
import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocalRender implements DataParser.OnValidateError{

    private static LocalRender localRender ;
    private LinearLayout viewContainer;
    private LocalRenerConfig localRenerConfig;
    private ServerDataProvider serverDataProvider;
    private Activity activity;

    private PopupWindow popupWindow;



    private LocalRender(){
    }

    public static LocalRender getInstance(){
        if (localRender==null){
            localRender = new LocalRender();
            localRender.serverDataProvider = new ServerDataProvider();
        }
        return localRender;

    }

    public void bind(Activity activity,LinearLayout viewGroup,LocalRenerConfig localRenerConfig){
        this.viewContainer =  viewGroup;
        this.localRenerConfig = localRenerConfig;
        this.activity = activity;
    }

    public void release(){
        viewContainer.removeAllViews();
        serverDataProvider.cleanAll();
        activity = null;
    }


    public void setData(String jsonData){
        if (TextUtils.isEmpty(jsonData)) {
            return;
        }
        serverDataProvider.init(jsonData);
        serverDataProvider.setOnValidateError(localRender);
        parseFieldComplete();
    }

    public void parseFieldComplete() {
        List<DataParser> dataParserList = serverDataProvider.getDataParsers();
        for (int i = 0; i < dataParserList.size(); i++) {
            DataParser dataParser = dataParserList.get(i);
            if (TextUtils.equals(dataParser.getType(), TextString.TAG)) {
                updateTextString((TextString) dataParser);
            } else if (TextUtils.equals(dataParser.getType(), MemoString.TAG)) {
                updateMemoString((MemoString) dataParser);
            } else if (TextUtils.equals(dataParser.getType(), DateSelect.TAG)) {
                updateDateSelect((DateSelect) dataParser);
            } else if (TextUtils.equals(dataParser.getType(), MultiSelect.TAG)) {
                updateMultiSelect((MultiSelect) dataParser);
            } else if (TextUtils.equals(dataParser.getType(), Cordition.TAG)) {
                updateCordition((Cordition) dataParser);
            } else if (TextUtils.equals(dataParser.getType(), BooleanSelect.TAG)) {
                updateBooleanSelect((BooleanSelect) dataParser);
            } else if (TextUtils.equals(dataParser.getType(), TextInteger.TAG)) {
                updateTexInteger((TextInteger) dataParser);
            } else if (TextUtils.equals(dataParser.getType(), Select.TAG)) {
                Select select = (Select) dataParser;
                updateSelect(select);
            }
        }
        if (localRenerConfig.getRenderCallback()!=null){
            localRenerConfig.getRenderCallback().onReady();
        }
    }

    /**
     * ???????????????????????????itemView
     *
     * @param textString
     */
    public void updateTextString(final TextString textString) {
        View view = getItemView(textString.getName());
        TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
        final EditText tv_last = (EditText) view.findViewById(R.id.et_last);
        TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
        tv_first.setText(textString.getLebel() + ":");
        if (textString.getRequired() == 1) {
            tv_tag.setVisibility(View.VISIBLE);
            tv_last.setHint("?????????");
        } else {
            tv_tag.setVisibility(View.INVISIBLE);
            tv_last.setHint("");
        }
        tv_last.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textString.setValue(s.toString());
            }
        });

        tv_last.setCompoundDrawables(null, null, null, null);

    }


    /**
     * ???????????????????????????????????????itemView
     *
     * @param cordition
     */
    public void updateCordition(final Cordition cordition) {
        View view = getItemView(cordition.getName());
        TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
        final EditText tv_last = (EditText) view.findViewById(R.id.et_last);
        TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
        tv_first.setText(cordition.getLebel() + ":");
        if (cordition.getRequired() == 1) {
            tv_tag.setVisibility(View.VISIBLE);
            tv_last.setHint("?????????");
        } else {
            tv_tag.setVisibility(View.INVISIBLE);
            tv_last.setHint("");
        }
        tv_last.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cordition.setValue(s.toString());
            }
        });
        tv_last.setCompoundDrawables(null, null, null, null);

        ImageView iv = (ImageView) view.findViewById(R.id.iv_map);
        iv.setVisibility(View.VISIBLE);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //map
            }
        });
    }

    public void updateMemoString(final MemoString memoString) {
        View view = getItemView(memoString.getName());
        TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
        final EditText tv_last = (EditText) view.findViewById(R.id.et_last);
        tv_last.setSingleLine(false);
        tv_last.setMinLines(3);
        TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
        tv_first.setText(memoString.getLebel() + ":");
        if (memoString.getRequired() == 1) {
            tv_tag.setVisibility(View.VISIBLE);
            tv_last.setHint("?????????");
        } else {
            tv_tag.setVisibility(View.INVISIBLE);
            tv_last.setHint("");
        }
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
        tv_last.setCompoundDrawables(null, null, null, null);
    }

    /**
     * ?????????????????????itemView
     *
     * @param select
     */
    public void updateSelect(final Select select) {
        View view = getItemView(select.getName());
        TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
        final EditText tv_last = (EditText) view.findViewById(R.id.et_last);
        tv_last.setCursorVisible(false);
        tv_last.setFocusable(false);
        tv_last.setFocusableInTouchMode(false);
        TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
        tv_first.setText(select.getLebel() + ":");
        if (select.getRequired() == 1) {
            tv_tag.setVisibility(View.VISIBLE);
            tv_last.setHint("?????????");
        } else {
            tv_tag.setVisibility(View.INVISIBLE);
            tv_last.setHint("");
        }

        if (select.getDomainType().equals("domain")) {
            tv_last.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showSelectPopupWindow(tv_last, select);
                }

            });
        }
    }


    public void updateTexInteger(final TextInteger textInteger) {
        View view = getItemView(textInteger.getName());
        TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
        final EditText tv_last = (EditText) view.findViewById(R.id.et_last);
        TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
        tv_first.setText(textInteger.getLebel() + ":");
        if (textInteger.getRequired() == 1) {
            tv_tag.setVisibility(View.VISIBLE);
            tv_last.setHint("?????????");
        } else {
            tv_tag.setVisibility(View.INVISIBLE);
            tv_last.setHint("");
        }
        tv_last.setInputType(InputType.TYPE_CLASS_NUMBER);
        tv_last.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textInteger.setValue(s.toString());
            }
        });

        tv_last.setCompoundDrawables(null, null, null, null);
    }


    /**
     * ?????????????????????itemView
     *
     * @param select
     */
    public void updateBooleanSelect(final BooleanSelect select) {
        View view = getItemView(select.getName());
        TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
        final EditText tv_last = (EditText) view.findViewById(R.id.et_last);
        tv_last.setCursorVisible(false);
        tv_last.setFocusable(false);
        tv_last.setFocusableInTouchMode(false);
        TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
        tv_first.setText(select.getLebel() + ":");
        if (select.getRequired() == 1) {
            tv_tag.setVisibility(View.VISIBLE);
            tv_last.setHint("?????????");
        } else {
            tv_tag.setVisibility(View.INVISIBLE);
            tv_last.setHint("");
        }

        tv_last.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showBooleanSelectPopupWindow(tv_last, select);
            }

        });

    }

    /**
     * ?????????????????????itemView
     *
     * @param multiSelect
     */
    public void updateMultiSelect(final MultiSelect multiSelect) {
        View view = getItemView(multiSelect.getName());
        TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
        final EditText tv_last = (EditText) view.findViewById(R.id.et_last);
        tv_last.setCursorVisible(false);
        tv_last.setFocusable(false);
        tv_last.setFocusableInTouchMode(false);
        TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
        tv_first.setText(multiSelect.getLebel() + ":");
        if (multiSelect.getRequired() == 1) {
            tv_tag.setVisibility(View.VISIBLE);
            tv_last.setHint("?????????");
        } else {
            tv_tag.setVisibility(View.INVISIBLE);
            tv_last.setHint("");
        }
        tv_last.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MultiSelectPop<MultiSelect.Element> multiSelectPop = new MultiSelectPop<MultiSelect.Element>();
                multiSelectPop.init(activity, multiSelect.getLebel(), multiSelect.getDomains(), multiSelect.getSelectPoses());
                multiSelectPop.show(viewContainer, new MultiSelectPop.Listener<MultiSelect.Element>() {
                    @Override
                    public String getItemTextShow(MultiSelect.Element element) {
                        return element.getText();
                    }

                    @Override
                    public void onFinish(List<Integer> selectedPoses) {
                        multiSelect.setSelectPoses(selectedPoses);
                        if (selectedPoses != null && selectedPoses.size() > 0) {
                            StringBuilder stringBuilder = new StringBuilder();
                            StringBuilder stringBuilderValue = new StringBuilder();
                            for (int i = 0; i < selectedPoses.size(); i++) {
                                stringBuilder.append(multiSelect.getDomains().get(selectedPoses.get(i)).getText());
                                stringBuilderValue.append(multiSelect.getDomains().get(selectedPoses.get(i)).getValue());
                                if (i != selectedPoses.size() - 1) {
                                    stringBuilder.append(",");
                                    stringBuilderValue.append(",");
                                }
                            }
                            multiSelect.setValue(stringBuilderValue.toString());
                            tv_last.setText(stringBuilder.toString());
                        } else {
                            tv_last.setText("");
                            multiSelect.setValue("");
                        }
                    }
                });

            }

        });
    }

    /**
     * ??????????????????????????????itemView
     *
     * @param dateSelect
     */
    public void updateDateSelect(final DateSelect dateSelect) {
        View view = getItemView(dateSelect.getName());
        TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
        final EditText tv_last = (EditText) view.findViewById(R.id.et_last);
        tv_last.setCursorVisible(false);
        tv_last.setFocusable(false);
        tv_last.setFocusableInTouchMode(false);
        TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
        tv_first.setText(dateSelect.getLebel() + ":");
        if (dateSelect.getRequired() == 1) {
            tv_tag.setVisibility(View.VISIBLE);
            tv_last.setHint("?????????");
        } else {
            tv_tag.setVisibility(View.INVISIBLE);
            tv_last.setHint("");
        }
        tv_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerView(tv_last, dateSelect);
            }
        });
    }


    /**
     * ???????????????itemView,?????????tag
     *
     * @param tag ??????????????? name
     * @return
     */
    public View getItemView(String tag) {
        View view =  LayoutInflater.from(viewContainer.getContext()).inflate(R.layout.item_form_people_data, null, false);
        viewContainer.addView(view);
        view.setTag(tag);
        return view;
    }


    /**
     * ????????????
     *
     * @param textView
     * @param select
     */
    private void showBooleanSelectPopupWindow(final TextView textView, final BooleanSelect select) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        } else {
            View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_people, null);
            int width = activity.getWindowManager().getDefaultDisplay().getWidth();
            popupWindow = new PopupWindow(contentView, width - width / 4, width);
            View close = contentView.findViewById(R.id.event_close);
            AnimationUtils.darkBackgroundColor(activity.getWindow(), 0.5f);
            TextView title = (TextView) contentView.findViewById(R.id.title);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
            });
            title.setText(select.getLebel());
            ListView listView = (ListView) contentView.findViewById(R.id.grid_listView);
            List<String> list = new ArrayList<>();
            list.add("???");
            list.add("???");
            MyBaseAdapter<String> myBaseAdapter = new MyBaseAdapter<String>(activity, list, R.layout.text_view) {
                @Override
                public void bindData(ViewHolder viewHolder, String element, int i) {
                    TextView tv = viewHolder.getView(R.id.text);
                    tv.setText(element);
                }
            };
            listView.setAdapter(myBaseAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        textView.setText("???");
                        select.setValue("1");
                    } else {
                        textView.setText("???");
                        select.setValue("0");
                    }
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
            });
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    AnimationUtils.darkBackgroundColor(activity.getWindow(), 1.0f);
                }
            });
            popupWindow.showAtLocation(viewContainer, Gravity.CENTER, 0, 0);
        }

    }

    /**
     * ????????????
     *
     * @param textView
     * @param select
     */
    private void showSelectPopupWindow(final TextView textView, final Select select) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        } else {
            View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_people, null);
            int width = activity.getWindowManager().getDefaultDisplay().getWidth();
            popupWindow = new PopupWindow(contentView, width - width / 4, width);
            View close = contentView.findViewById(R.id.event_close);
            AnimationUtils.darkBackgroundColor(activity.getWindow(), 0.5f);
            TextView title = (TextView) contentView.findViewById(R.id.title);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
            });
            title.setText(select.getLebel());
            ListView listView = (ListView) contentView.findViewById(R.id.grid_listView);
            MyBaseAdapter<Select.Element> myBaseAdapter = new MyBaseAdapter<Select.Element>(activity, select.getDomains(), R.layout.text_view) {
                @Override
                public void bindData(ViewHolder viewHolder, Select.Element element, int i) {
                    TextView tv = viewHolder.getView(R.id.text);
                    tv.setText(element.getText());
                }
            };
            listView.setAdapter(myBaseAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Select.Element element = select.getDomains().get(position);
                    textView.setText(element.getText());
                    select.setValue(element.getValue());
                    Log.d("yeying", "this is addmain activity  set value is " + element.getValue());
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
            });
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    AnimationUtils.darkBackgroundColor(activity.getWindow(), 1.0f);
                }
            });
            popupWindow.showAtLocation(viewContainer, Gravity.CENTER, 0, 0);
        }

    }

    /**
     * ????????????
     *
     * @param tv
     * @param dateSelect
     */
    public void showTimePickerView(final TextView tv, final DateSelect dateSelect) {
        final SimpleDateFormat format = new SimpleDateFormat(dateSelect.getDateFormatStr());

        TimePickerView pvTime = new TimePickerView.Builder(activity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//??????????????????
                String time = format.format(date2);
                tv.setText(time);
                dateSelect.setValue(time);
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)//??????????????????
                .setCancelText("??????")//??????????????????
                .setSubmitText("??????")//??????????????????
                .setContentSize(20)//??????????????????
                .setTitleSize(20)//??????????????????
//              .setTitleText("???????????????")//????????????
                .setOutSideCancelable(true)//???????????????????????????????????????????????????????????????
                .isCyclic(true)//??????????????????
                .setTextColorCenter(Color.BLACK)//????????????????????????
                .setTitleColor(Color.BLACK)//??????????????????
                .setSubmitColor(Color.RED)//????????????????????????
                .setCancelColor(Color.GRAY)//????????????????????????
//              .setTitleBgColor(0xFF666666)//?????????????????? Night mode
//              .setBgColor(0xFF333333)//?????????????????? Night mode
//              .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//?????????1900-2100???
//              .setDate(selectedDate)// ?????????????????????????????????????????????*/
//              .setRangDate(startDate,endDate)//???????????????????????????
//              .setLabel("???","???","???","???","???","???")
                .setLabel("???", "???", "???", "???", "???", "???")
                .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
//              .isDialog(true)//??????????????????????????????
                .build();
        pvTime.setDate(Calendar.getInstance());//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        pvTime.show();
    }



    /**
     * ???????????????????????????update???
     */
    public void submit() {

    }

    /**
     * ????????????
     *
     * @return
     */
//    public PeopleDataOperation getPeopleDataOperation() {
//        return (PeopleDataOperation) MvpManager.findPresenter(this);
//    }

    /**
     * ????????????????????????
     *
     * @param type    item??????
     * @param label   item???????????????????????????"??????"
     * @param name    item????????? ??? name
     * @param message ?????????????????????
     */
    @Override
    public void validateError(String type, String label, String name, String message) {
//        LogCat.d("type " + type + " label" + label + " name" + name + " message" + message);
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
    }


    /**
     * ????????????itemView
     *
     * @param name         ??????????????????
     * @param showText     ???????????????
     * @param value        ??????????????????
     * @param canNotModify ??????????????????
     */
    public void updateItemView(String name, String showText, String value, boolean canNotModify) {
        View itemView = viewContainer.findViewWithTag(name);
        EditText et_last = null;
        if (itemView != null) {
            et_last = (EditText) itemView.findViewById(R.id.et_last);
            et_last.setText(showText);
            if (!localRenerConfig.isAdd() && canNotModify) {
                et_last.setEnabled(false);
            } else {
                et_last.setEnabled(true);
            }
        }
        if (TextUtils.equals(name, "gender")) {
            if (et_last != null) {
                et_last.setEnabled(false);
            }
        }
    }

    public void updateItem(String name, String showText, String value) {
        Log.d("yeying", "this is updateItem" + "name is " + name + " showText " + showText + " value " + value);
        DataParser dataParser = serverDataProvider.updateItemValue(name, value);
        if (dataParser != null) {
            if (TextUtils.isEmpty(showText)) {
                showText = dataParser.getShowTextFromValue();
            }
            //??????????????????
            if (dataParser.getName().equals("gId") && !TextUtils.isEmpty(showText)) {
                Select select = (Select) dataParser;
                select.setExtra(showText);
            }
            localRender.updateItemView(name, showText, value, dataParser.getView() == 1);
        }
    }

    public boolean validateAll(){
        return serverDataProvider.validateAll();
    }

    public ServerDataProvider getServerDataProvider() {
        return serverDataProvider;
    }
}
