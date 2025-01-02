package com.example.plateful.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plateful.Models.Step;
import com.example.plateful.R;

import java.util.List;

public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepViewHolder>{
    Context context;
    List<Step> list;

    public InstructionStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {

        holder.textView_instruction_step_number.setText(String.valueOf(list.get(position).number));
        holder.textView_instruction_step_title.setText(list.get(position).step);

        holder.recycler_instruction_Ingredients.setHasFixedSize(true);
        holder.recycler_instruction_Ingredients.setLayoutManager( new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionsIngredientsAdapter instructionsIngredientsAdapter= new InstructionsIngredientsAdapter(context, list.get(position).ingredients);
        holder.recycler_instruction_Ingredients.setAdapter(instructionsIngredientsAdapter);

        holder.recycler_instruction_equipments.setHasFixedSize(true);
        holder.recycler_instruction_equipments.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionsEquipmentsAdapter instructionsEquipmentsAdapter= new InstructionsEquipmentsAdapter(context,list.get(position).equipment);
        holder.recycler_instruction_equipments.setAdapter(instructionsEquipmentsAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionStepViewHolder extends RecyclerView.ViewHolder{

    TextView textView_instruction_step_number,textView_instruction_step_title;
    RecyclerView recycler_instruction_equipments,recycler_instruction_Ingredients;
    public InstructionStepViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_instruction_step_number=itemView.findViewById(R.id.textView_instruction_step_number);
        textView_instruction_step_title=itemView.findViewById(R.id.textView_instruction_step_title);
        recycler_instruction_equipments=itemView.findViewById(R.id.recycler_instruction_equipments);
        recycler_instruction_Ingredients=itemView.findViewById(R.id.recycler_instruction_Ingredients);
    }
}