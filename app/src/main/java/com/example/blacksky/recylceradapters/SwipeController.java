package com.example.blacksky.recylceradapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.MotionEvent;
import android.view.View;
import com.example.blacksky.R;

import static android.support.v7.widget.helper.ItemTouchHelper.*;

enum ButtonState {
    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}

public class SwipeController extends Callback {

    private boolean swipeBack = false;
    private ButtonState buttonShowedState = ButtonState.GONE;
    private RectF buttonInstance = null;
    private RecyclerView.ViewHolder currentItemViewHolder = null;
    private SwipeControllerActions buttonsActions = null;
    private static final float buttonWidth = 150;
    private static final float buttonHeight = 100;
    private Context context;

    public SwipeController(SwipeControllerActions buttonsActions, Context context) {
        this.buttonsActions = buttonsActions;
        this.context = context;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = buttonShowedState != ButtonState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState != ButtonState.GONE) {
                if (buttonShowedState == ButtonState.LEFT_VISIBLE) dX = Math.max(dX, buttonWidth);
                if (buttonShowedState == ButtonState.RIGHT_VISIBLE) dX = Math.min(dX, -buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }else{
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (buttonShowedState == ButtonState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        currentItemViewHolder = viewHolder;

    }

    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if (swipeBack) {
                    if (dX < -buttonWidth) buttonShowedState = ButtonState.RIGHT_VISIBLE;
                    else if (dX > buttonWidth) buttonShowedState  = ButtonState.LEFT_VISIBLE;

                    if (buttonShowedState != ButtonState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

                    if (buttonsActions != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {
                        if (buttonShowedState == ButtonState.LEFT_VISIBLE) {
                            buttonsActions.onLeftClicked(viewHolder.getAdapterPosition());
                        }
                        else if (buttonShowedState == ButtonState.RIGHT_VISIBLE) {
                            buttonsActions.onRightClicked(viewHolder.getAdapterPosition());
                        }
                    }
                    buttonShowedState = ButtonState.GONE;
                    currentItemViewHolder = null;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder){
        float buttonWithOutPadding = buttonWidth - 20;
        float corners = 16;

        View itemView = viewHolder.itemView;
        Paint p = new Paint();

        RectF confirmButton = new RectF(itemView.getLeft(), itemView.getTop() + 20, itemView.getLeft() + buttonWithOutPadding, itemView.getBottom() - 20);
        p.setColor(ContextCompat.getColor(context, R.color.colorAccentBlue));
        c.drawRoundRect(confirmButton, corners, corners, p);
        drawText("Con", c, confirmButton, p);

        RectF deleteButton = new RectF(itemView.getRight() - buttonWithOutPadding, itemView.getTop() + 20, itemView.getRight(), itemView.getBottom() - 20);
        p.setColor(ContextCompat.getColor(context, R.color.red));
        c.drawRoundRect(deleteButton, corners, corners, p);
        drawText("Del", c, deleteButton, p);

        buttonInstance = null;
        if (buttonShowedState == ButtonState.LEFT_VISIBLE) {
            buttonInstance = confirmButton;
        }
        else if (buttonShowedState == ButtonState.RIGHT_VISIBLE) {
            buttonInstance = deleteButton;
        }
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
    }

    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder);
        }
    }
}





















