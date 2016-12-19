//creates a project for the website given as input from terminal and launches Site Audit
var Nightmare = require('nightmare');

	nightmare = Nightmare();
	nightmare.goto('http://localhost:8080/asqatasun/login.html')
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
	.type('input#label.xlarge',process.argv[2])
	.type('input#contractUrl.xlarge',process.argv[2])
	.click('input#referentialMapRgaa301')
	.click('input#functionalityMapDOMAIN1')
	.click('input.btn.primary')
	.wait(1000)
	.click('html body#tgm-contracts-mngt div.container ul.breadcrumb li a')
    .type('input#label-inclusion-choice.medium',process.argv[2])
    .click('input.update-action')
    .wait(1000)
    .click('html body#tgm-home div.container div.row div.span16 table.project-table tbody tr#project-0.one-project.last-project td.project-actions span.action-button a img.action-s')
    .wait('body#tgm-site-set-up')
    .select('select#level.xlarge',"Rgaa30;LEVEL_3")
    .click('input#launch-audit-submit.large.awesome.orange.xlarge')
	.screenshot('asq.png')
    
    .end()
    .then(
		function (err, nightmare) 
		{
			if (err) return console.log(err);
			console.log('Done!');
		}
	);
