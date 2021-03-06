var data = require("self").data;
var pageMod = require("page-mod");

pageMod.PageMod({
	include: "http://xarch.us.oracle.com/*",
	contentScriptWhen: 'start',
	contentScriptFile: [
		data.url("syntaxhighlighter_scripts/shCore.min.js"),
		data.url("syntaxhighlighter_scripts/shBrushAppleScript.js"),
		data.url("syntaxhighlighter_scripts/shBrushAS3.js"),
		data.url("syntaxhighlighter_scripts/shBrushBash.js"),
		data.url("syntaxhighlighter_scripts/shBrushColdFusion.js"),
		data.url("syntaxhighlighter_scripts/shBrushCpp.js"),
		data.url("syntaxhighlighter_scripts/shBrushCSharp.js"),
		data.url("syntaxhighlighter_scripts/shBrushCss.js"),
		data.url("syntaxhighlighter_scripts/shBrushDelphi.js"),
		data.url("syntaxhighlighter_scripts/shBrushDiff.js"),
		data.url("syntaxhighlighter_scripts/shBrushErlang.js"),
		data.url("syntaxhighlighter_scripts/shBrushGroovy.js"),
		data.url("syntaxhighlighter_scripts/shBrushJava.js"),
		data.url("syntaxhighlighter_scripts/shBrushJavaFX.js"),
		data.url("syntaxhighlighter_scripts/shBrushJScript.js"),
		data.url("syntaxhighlighter_scripts/shBrushPerl.js"),
		data.url("syntaxhighlighter_scripts/shBrushPhp.js"),
		data.url("syntaxhighlighter_scripts/shBrushPlain.js"),
		data.url("syntaxhighlighter_scripts/shBrushPython.js"),
		data.url("syntaxhighlighter_scripts/shBrushRuby.js"),
		data.url("syntaxhighlighter_scripts/shBrushSass.js"),
		data.url("syntaxhighlighter_scripts/shBrushScala.js"),
		data.url("syntaxhighlighter_scripts/shBrushSql.js"),
		data.url("syntaxhighlighter_scripts/shBrushVb.js"),
		data.url("syntaxhighlighter_scripts/shBrushPlSql.js"),
		data.url("syntaxhighlighter_scripts/shBrushXml.js"),
		data.url("jquery-1.8.2.min.js"),
		data.url("jquery-ui-1.9.1.custom.min.js"),
		data.url("jquery-ui-combobox-autocomplete.min.js"),
		data.url("jquery.layout.min.js"),
		data.url("better-source-search.min.js")
	],
	contentStyleFile: [
		data.url("syntaxhighlighter_styles/shCore.css"),
		data.url("syntaxhighlighter_styles/shThemeDefault.css"),
		data.url("jquery_ui/jquery-ui-1.9.1.custom.css")
	],
	contentStyle: [
		".ui-widget-content {background: #eeeeee url('"+data.url("jquery_ui/images/ui-bg_highlight-soft_100_eeeeee_1x100.png")+"') 50% top repeat-x;}",
		".ui-widget-header {background: #f6a828 url('"+data.url("jquery_ui/images/ui-bg_gloss-wave_35_f6a828_500x100.png")+"') 50% 50% repeat-x; }",
		".ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {background: #f6f6f6 url('"+data.url("jquery_ui/images/ui-bg_glass_100_f6f6f6_1x400.png")+"') 50% 50% repeat-x;}",
		".ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus {background: #fdf5ce url('"+data.url("jquery_ui/images/ui-bg_glass_100_fdf5ce_1x400.png")+"') 50% 50% repeat-x;}",
		".ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active {background: #ffffff url('"+data.url("jquery_ui/images/ui-bg_glass_65_ffffff_1x400.png")+"') 50% 50% repeat-x;}",
		".ui-state-highlight, .ui-widget-content .ui-state-highlight, .ui-widget-header .ui-state-highlight  {background: #ffe45c url('"+data.url("jquery_ui/images/ui-bg_highlight-soft_75_ffe45c_1x100.png")+"') 50% top repeat-x; }",
		".ui-state-error, .ui-widget-content .ui-state-error, .ui-widget-header .ui-state-error { background: #b81900 url('"+data.url("jquery_ui/images/ui-bg_diagonals-thick_18_b81900_40x40.png")+"') 50% 50% repeat; }",
		".ui-icon {background-image: url('"+data.url("jquery_ui/images/ui-icons_222222_256x240.png")+"'); }",
		".ui-widget-content .ui-icon {background-image: url('"+data.url("jquery_ui/images/ui-icons_222222_256x240.png")+"'); }",
		".ui-widget-header .ui-icon {background-image: url('"+data.url("jquery_ui/images/ui-icons_ffffff_256x240.png")+"'); }",
		".ui-state-default .ui-icon { background-image: url('"+data.url("jquery_ui/images/ui-icons_ef8c08_256x240.png")+"'); }",
		".ui-state-hover .ui-icon, .ui-state-focus .ui-icon {background-image: url('"+data.url("jquery_ui/images/ui-icons_ef8c08_256x240.png")+"'); }",
		".ui-state-active .ui-icon {background-image: url('"+data.url("jquery_ui/images/ui-icons_ef8c08_256x240.png")+"'); }",
		".ui-state-highlight .ui-icon {background-image: url('"+data.url("jquery_ui/images/ui-icons_228ef1_256x240.png")+"'); }",
		".ui-state-error .ui-icon, .ui-state-error-text .ui-icon {background-image: url('"+data.url("jquery_ui/images/ui-icons_ffd27a_256x240.png")+"'); }",
		".ui-widget-overlay { background: #666666 url('"+data.url("jquery_ui/images/ui-bg_diagonals-thick_20_666666_40x40.png")+"') 50% 50% repeat; }",
		".ui-widget-shadow { background: #000000 url('"+data.url("jquery_ui/images/ui-bg_flat_10_000000_40x100.png")+"') 50% 50% repeat-x; }"
	]
});
