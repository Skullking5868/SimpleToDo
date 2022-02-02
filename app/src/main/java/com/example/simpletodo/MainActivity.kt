package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.AbsListView
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils.writeLines
import org.apache.commons.io.IOUtils.writeLines
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
               //remove the item from list
                listOfTasks.removeAt(position)
                //notifiy the adapter that it changed
                adapter.notifyDataSetChanged()

                saveItems()

            }

        }

        //1. let's detect when the user clicks on the add button
        //findViewById<Button>(R.id.button).setOnClickListener {
        //Code in here is going to be executed when the user clicks on the button
        // Log.i("Caren","User click on button")
        loadItems()

        

        //look up recyclerView layout

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //Creat adapter passing in the sampmle user data

        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        //Attach the adapter to the recliclerView to populate items

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up the button and input field so that the can enter a task
        val  inputTextField = findViewById<EditText>(R.id.addTaskField)
        //get a refrence for the button
//and set an onclick listener
        findViewById<Button>(R.id.button).setOnClickListener{
            //grabs texts addTaskField
            val userInputtedTask = inputTextField.text.toString()
            //add s tring to our list listOfTasksf
            listOfTasks.add(userInputtedTask)

            //notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //reset text field
            inputTextField.setText("")

            saveItems()
        }

        }

    // save the data the user inputed
    //by reading and writing the file
    //create method to get the file we need

    fun getDataFile() : File {

        //every lind is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    //load the items by reading
    fun loadItems() {
        try{
    listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
    } catch (ioException:IOException){
        ioException.printStackTrace()

        }    }


    //save items by writing them into our data file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }


}