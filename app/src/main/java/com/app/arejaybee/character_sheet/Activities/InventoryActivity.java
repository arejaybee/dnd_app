package com.app.arejaybee.character_sheet.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.DataObjects.InventoryItem;
import com.app.arejaybee.character_sheet.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class InventoryActivity extends AbstractCharacterActivity {
    private RelativeLayout selectedInventory;
    private int selectedInventoryIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Build the Inventory Section
        buildInventoryLayout();

        //Build the other two sections
        buildOtherLayouts();
    }


    private void buildOtherLayouts() {
        EditText money = findViewById(R.id.iet_money);
        TextView weight = findViewById(R.id.itv_weight);
        money.setText(rob.getCurrency());
        weight.setText(Integer.toString(rob.getInventoryWeight()));

        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rob.setCurrency(s.toString());
                rob.saveCharacter(context);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void buildInventoryLayout() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 15);

        LinearLayout ll = findViewById(R.id.ill_inventoryLayout);
        ArrayList<InventoryItem> items = rob.getItems();
        ll.removeAllViews();
        for (int i = 0; i < items.size(); i++) {
            RelativeLayout inventoryRow = new RelativeLayout(this);
            inventoryRow.setLayoutParams(lp);
            inventoryRow.setBackgroundResource(R.drawable.character_border);

            RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rllp.addRule(RelativeLayout.CENTER_VERTICAL);
            rllp.setMargins(convertSpToPixels(10), 10, convertSpToPixels(10), 10);

            LinearLayout llInner = buildInnerInventoryLayout(rob.getItemAt(i), i);

            ImageView iv = new ImageView(this);
            iv.setLayoutParams(rllp);
            Bitmap bm = getSpinnerValue(llInner);

            if (bm != null) {
                bm = Bitmap.createScaledBitmap(bm, 128, 128, true);
                iv.setImageBitmap(bm);
            }

            EditText etChar = new EditText(this);
            etChar.setText(items.get(i).getName());
            etChar.setTextSize(16);
            rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            rllp.setMargins(convertSpToPixels(50), 10, convertSpToPixels(50), 10);
            etChar.setLayoutParams(new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            etChar.setId(View.generateViewId());
            etChar.setLayoutParams(rllp);
            etChar.setGravity(Gravity.CENTER_HORIZONTAL);
            int index = i;
            etChar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    InventoryItem w = rob.getItemAt(index);
                    w.setName(getTextFromView(etChar));
                    rob.setItemAt(index, w);
                }
            });

            ImageButton btnAbilityDropdown = new ImageButton(this);
            btnAbilityDropdown.setImageResource(android.R.drawable.ic_menu_more);
            rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rllp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rllp.addRule(RelativeLayout.ALIGN_TOP, etChar.getId());
            rllp.addRule(RelativeLayout.ALIGN_BOTTOM, etChar.getId());
            btnAbilityDropdown.setLayoutParams(rllp);
            btnAbilityDropdown.setBackgroundColor(Color.TRANSPARENT);
            btnAbilityDropdown.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LinearLayout innerLayout = (LinearLayout) ((RelativeLayout) v.getParent()).getChildAt(3);
                    if (innerLayout.getVisibility() != View.GONE) {
                        if (!((Spinner) ((LinearLayout) innerLayout.getChildAt(1)).getChildAt(0)).getSelectedItem().equals("NONE")) {
                            ImageView iv = (ImageView) ((RelativeLayout) v.getParent()).getChildAt(0);
                            Bitmap bm = getSpinnerValue(innerLayout);
                            bm = Bitmap.createScaledBitmap(bm, 128, 128, true);
                            iv.setImageBitmap(bm);

                            toggleViewVisibility(v, iv);
                            btnAbilityDropdown.setRotation(180);
                        }
                    } else if (((RelativeLayout) v.getParent()).getChildAt(0).getVisibility() != View.GONE) {
                        toggleViewVisibility(v, ((RelativeLayout) v.getParent()).getChildAt(0));
                        btnAbilityDropdown.setRotation(180);
                    }

                    toggleViewVisibility(v, innerLayout);
                    RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    rllp.setMargins(30, 15, 30, 20);
                    rllp.addRule(RelativeLayout.BELOW, etChar.getId());
                    innerLayout.setLayoutParams(rllp);
                    buildOtherLayouts();
                }
            });


            inventoryRow.addView(iv);
            inventoryRow.addView(etChar);
            inventoryRow.addView(btnAbilityDropdown);

            inventoryRow.addView(llInner);
            btnAbilityDropdown.setRotation(180);


            inventoryRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedInventory != null) {
                        selectedInventory.setBackgroundResource(R.drawable.character_border);
                    }
                    v.setBackgroundResource(R.drawable.selected_character_border);
                    selectedInventory = (RelativeLayout) v;
                    selectedInventoryIndex = index;
                }
            });


            toggleViewVisibility(btnAbilityDropdown, ((RelativeLayout) btnAbilityDropdown.getParent()).getChildAt(3));

            ll.addView(inventoryRow, ll.getChildCount() - 1);
        }
    }

    private Bitmap getSpinnerValue(LinearLayout llInner) {
        switch (((Spinner) ((LinearLayout) llInner.getChildAt(1)).getChildAt(0)).getSelectedItem().toString()) {
            case ("BELT"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.belt);
            case ("BODY"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.armor);
            case ("CHEST"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.vest);
            case ("EYES"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.eyes);
            case ("FEET"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.feet);
            case ("HANDS"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.hand);
            case ("HEAD"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.head);
            case ("HEADBAND"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.headband);
            case ("NECK"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.necklace);
            case ("RING"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.ring);
            case ("SHIELD"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.shield);
            case ("SHOULDERS"):
                return BitmapFactory.decodeResource(getResources(), R.drawable.shoulder);
            default:
                return null;
        }
    }

    private LinearLayout buildInnerInventoryLayout(InventoryItem item, int index) {
        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        ll.setLayoutParams(lp);
        //Row of labels for fields
        LinearLayout labelRow = new LinearLayout(this);
        labelRow.setLayoutParams(lp);
        labelRow.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams innerLP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );


        LinearLayout.LayoutParams innerLP2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        innerLP2.setMargins(convertSpToPixels(5), 0, 0, 0);
        TextView tvSlot = new TextView(this);
        tvSlot.setText("Slot");
        tvSlot.setLayoutParams(innerLP2);
        tvSlot.setWidth(convertSpToPixels(160));
        labelRow.addView(tvSlot);
        TextView tvWeight = new TextView(this);
        tvWeight.setText("Weight");
        tvWeight.setWidth(convertSpToPixels(75));
        tvWeight.setLayoutParams(innerLP2);
        labelRow.addView(tvWeight);
        TextView tvCost = new TextView(this);
        tvCost.setText("Cost");
        tvCost.setWidth(convertSpToPixels(75));
        tvCost.setLayoutParams(innerLP2);
        labelRow.addView(tvCost);

        //Row of edit fields
        LinearLayout editRow = new LinearLayout(this);
        editRow.setLayoutParams(lp);
        editRow.setOrientation(LinearLayout.HORIZONTAL);

        Spinner spSlot = new Spinner(this);
        spSlot.setPadding(5, 0, 5, 0);
        tvSlot.setLayoutParams(innerLP2);
        spSlot.setGravity(Gravity.LEFT);
        spSlot.setDropDownWidth(convertSpToPixels(75));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.itemSlots, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSlot.setAdapter(adapter);
        spSlot.setDropDownWidth(convertSpToPixels(160));
        spSlot.setSelection(rob.getItemAt(index).getSlotIndex());


        spSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item.setSlot(InventoryItem.SlotEnum.valueOf(getResources().getStringArray(R.array.itemSlots)[position]));
                rob.setItemAt(index, item);
                rob.saveCharacter(context);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        EditText etWeight = new EditText(this);
        etWeight.setText(Float.toString(item.getWeight()));
        etWeight.setBackgroundResource(R.drawable.edit_text_border);
        etWeight.setGravity(Gravity.CENTER);
        etWeight.setPadding(5, 0, 5, 0);
        etWeight.setWidth(convertSpToPixels(75));
        etWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        etWeight.setLayoutParams(innerLP2);

        EditText etCost = new EditText(this);
        etCost.setText(item.getCost());
        etCost.setBackgroundResource(R.drawable.edit_text_border);
        etCost.setGravity(Gravity.CENTER);
        etCost.setPadding(5, 0, 5, 0);
        etCost.setWidth(convertSpToPixels(75));
        etCost.setLayoutParams(innerLP2);


        TextView tvDescription = new TextView(this);
        tvDescription.setText("Description");
        tvDescription.setLayoutParams(lp);

        EditText etDescription = new EditText(this);
        etDescription.setBackgroundResource(R.drawable.edit_text_border);
        etDescription.setMinHeight(convertSpToPixels(20));
        etDescription.setWidth(0);
        etDescription.setText(item.getDescription());
        etDescription.setPadding(convertSpToPixels(5), 0, convertSpToPixels(5), 0);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setCost(getTextFromView(etCost));
                try {
                    item.setWeight(Integer.parseInt(getTextFromView(etWeight)));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                item.setDescription(getTextFromView(etDescription));
                rob.setItemAt(index, item);
                rob.saveCharacter(context);
            }
        };
        etCost.addTextChangedListener(tw);
        etWeight.addTextChangedListener(tw);
        etDescription.addTextChangedListener(tw);
        editRow.addView(spSlot);
        editRow.addView(etWeight);
        editRow.addView(etCost);
        ll.addView(labelRow);
        ll.addView(editRow);
        ll.addView(tvDescription);
        ll.addView(etDescription);
        return ll;
    }

    public void onClickNew(View v) {
        updatePlayerFields();
        rob.addItem(new InventoryItem());
        rob.saveCharacter(this);
        buildInventoryLayout();
    }

    public void onClickDelete(View v) {
        if (selectedInventory == null) {
            makeLongToast("No weapon selected!");
            return;
        }

        rob.removeFromInventory(selectedInventoryIndex);
        rob.saveCharacter(this);
        buildInventoryLayout();
    }

}
