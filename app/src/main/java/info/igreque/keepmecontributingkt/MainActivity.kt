package info.igreque.keepmecontributingkt

import android.content.Intent
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

        btn.setOnClickListener {
            val userName = findViewById<EditText>(R.id.inputContributorName).text.toString()
            val repositoryName = findViewById<EditText>(R.id.inputRepositoryName).text.toString()
            val i = Intent()
            i.putExtra(ContributionStatusCheckerService.EXTRA_CONTRIBUTOR_NAME, userName)
            i.putExtra(ContributionStatusCheckerService.EXTRA_REPOSITORY_NAME, repositoryName)
            i.action = ContributionStatusCheckerService.ACTION_UPDATE_PREFERENCES_DONE
            ContributionStatusCheckerService.enqueueWork(applicationContext, i)
        }
    }
}
