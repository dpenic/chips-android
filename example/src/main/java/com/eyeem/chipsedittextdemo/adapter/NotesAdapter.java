package com.eyeem.chipsedittextdemo.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eyeem.chips.ChipsTextView;
import com.eyeem.chipsedittextdemo.R;
import com.eyeem.chipsedittextdemo.model.Note;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by vishna on 03/02/15.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {

   private final static boolean TRUNCATE = true;

   List<Note> notes;

   @Override public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      NoteHolder noteHolder =  new NoteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false));

      SpannableStringBuilder moreText = new SpannableStringBuilder("...");
      com.eyeem.chips.Utils.tapify(moreText, 0, moreText.length(), 0xff000000, 0xff000000, new Truncation());
      if (TRUNCATE) noteHolder.textView.setMaxLines(2, moreText);

      return noteHolder;
   }

   @Override public void onBindViewHolder(NoteHolder holder, int position) {
      Context context = holder.textView.getContext();
      TextPaint textPaint = noteTextPaint(context);
      holder.textView.setTextPaint(textPaint);
      holder.textView.setText(new SpannableString(notes.get(position).textSpan((int) textPaint.getTextSize(), context)));
      if (TRUNCATE)  holder.textView.setTruncated(true);
   }

   @Override public int getItemCount() {
      return notes == null ? 0 : notes.size();
   }

   static class NoteHolder extends RecyclerView.ViewHolder {

      @InjectView(R.id.note_text_view) ChipsTextView textView;

      public NoteHolder(View itemView) {
         super(itemView);
         ButterKnife.inject(this, itemView);
      }
   }

   public NotesAdapter setNotes(List<Note> notes) {
      this.notes = notes;
      notifyDataSetChanged();
      return this;
   }


   private static TextPaint _noteTextPaint;

   static TextPaint noteTextPaint(Context context){
      context = context.getApplicationContext();
      if (_noteTextPaint == null) {
         _noteTextPaint = new TextPaint();
         _noteTextPaint.setAntiAlias(true);
         Resources r = context.getResources();
         float _dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
         _noteTextPaint.setTextSize(_dp);
         _noteTextPaint.setColor(0xFF000000);
      }
      return _noteTextPaint;
   }

   private static class Truncation {
   } // marker class
}