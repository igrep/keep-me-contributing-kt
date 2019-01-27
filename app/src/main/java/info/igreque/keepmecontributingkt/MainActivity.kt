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
        val userName = findViewById<EditText>(R.id.inputContributorName).text
        val repositoryName = findViewById<EditText>(R.id.inputRepositoryName).text
        btn.setOnClickListener { model.startPolling(userName, repositoryName) }
    }
}
