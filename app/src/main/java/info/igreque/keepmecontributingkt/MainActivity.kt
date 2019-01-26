package info.igreque.keepmecontributingkt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ViewViaNotification.createWithNotificationChannel(applicationContext)
        val model = ContributionStatusChecker(view::show)

        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button)
        val name = findViewById<EditText>(R.id.contributorName).text
        val url = findViewById<EditText>(R.id.inputRepositoryUrl).text
        btn.setOnClickListener { model.startPolling(name, url) }
    }
}
