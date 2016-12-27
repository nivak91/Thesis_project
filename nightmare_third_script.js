//creates the projects defined in var projects and launches Page Audits
var Nightmare = require('nightmare');
vo = require('vo');
nightmare = Nightmare();

var run = function * () {
	var projects = ["http://www.oracle.com/technetwork/java/index.html", "https://www.joomla.org/" ];
	for(var i=0; i < projects.length; i++){	
	yield nightmare.goto('http://localhost:8080/asqatasun/login.html')
    .type('input#j_username.input-xlarge', 'nikovaka@ece.auth.gr')
    .type('input#j_password.input-xlarge', '0laZjWMu')
    .click('input.btn')
    .wait('body#tgm-home')
    .click('html body#tgm-home div.topbar div.fill div.container ul.nav.secondary-nav li a img')
    .wait('body#tgm-admin')
    .click('html body#tgm-admin div.container div.row div.span16.tg-table-container div table#user-list-table.tg-table.sortable-table tbody tr td.col08 a img')
	.wait('body#tgm-contracts-mngt')
	.click('a.btn.btn-primary')
	.wait('body#tgm-add-contract')
	.type('input#label.xlarge', projects[i])
	.type('input#contractUrl.xlarge', projects[i])
	.click('input#referentialMapRgaa301')
	//.click('input#functionalityMapPAGES1')
	.click('input#functionalityMapDOMAIN1')
	.click('input.btn.primary')
	.wait(1000)
	.click('html body#tgm-contracts-mngt div.container ul.breadcrumb li a')
    .type('input#label-inclusion-choice.medium', projects[i])
    .click('input.update-action')
    .wait(1000)
    .click('html body#tgm-home div.container div.row div.span16 table.project-table tbody tr#project-0.one-project.last-project td.project-actions span.action-button a img.action-s') 
    .wait('body#tgm-site-set-up')
    .select('select#level.xlarge',"Rgaa30;LEVEL_3")
    //.wait('body#tgm-page-set-up')
    .click('input#launch-audit-submit.large.awesome.orange.xlarge')
    .wait(10000)
    .click('img#logout-icon')
    .wait(1000)
	}
}
	/*.screenshot('asq.png')*/
    
    vo(run)(
		function (err, nightmare) 
		{
			if (err) return console.log(err);
			console.log('Done!');
		}
	);
