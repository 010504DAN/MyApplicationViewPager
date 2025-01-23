package com.example.myapplicationviewpager.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun <T: Any> Fragment.setBackStackData(kay :String, data :T, doBack :Boolean){
    findNavController().previousBackStackEntry?.savedStateHandle?.set(kay,data)
    if (doBack){
        findNavController().navigateUp()
    }
}

fun <T: Any> Fragment.getBackStackData(kay :String, result: (T) -> (Unit)){
    findNavController().previousBackStackEntry?.savedStateHandle?.getLiveData<T>(kay)
        ?.observe(viewLifecycleOwner){
            result(it)
        }

}