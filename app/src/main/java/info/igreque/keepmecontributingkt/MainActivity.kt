package info.igreque.keepmecontributingkt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button)
        val target = CheckTargetRepository(applicationContext).load()

        val viewContributorName = findViewById<TextView>(R.id.inputContributorName)
        viewContributorName.text = target.contributorName

        val viewRepositoryName = findViewById<TextView>(R.id.inputRepositoryName)
        viewRepositoryName.text = target.repositoryName

        val view = ViewViaNotification.createWithNotificationChannel(applicationContext)
        val repository = CheckTargetRepository(applicationContext)
        val scheduler = Scheduler(applicationContext)

        btn.setOnClickListener {
            val userName = findViewById<EditText>(R.id.inputContributorName).text.toString()
            val repositoryName = findViewById<EditText>(R.id.inputRepositoryName).text.toString()
            UpdateCheckTargetInteraction(view, repository).run(userName, repositoryName)

            scheduler.cancel() // Cancel the already running job.
            scheduler.schedule(ContributionStatusCheckerService::class.java)
        }
    }
}
