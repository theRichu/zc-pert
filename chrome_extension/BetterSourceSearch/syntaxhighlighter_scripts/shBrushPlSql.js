/**
 * SyntaxHighlighter
 * http://alexgorbatchev.com/SyntaxHighlighter
 *
 * SyntaxHighlighter is donationware. If you are using it, please donate.
 * http://alexgorbatchev.com/SyntaxHighlighter/donate.html
 *
 * @version
 * 3.0.83 (July 02 2010)
 *
 * @copyright
 * Copyright (C) 2004-2010 Alex Gorbatchev.
 *
 * @license
 * Dual licensed under the MIT and GPL licenses.
 */
;(function()
{
	// CommonJS
	typeof(require) != 'undefined' ? SyntaxHighlighter = require('shCore').SyntaxHighlighter : null;

	function Brush()
	{
		var funcs = 'abs acos add_months ascii asciistr asin atan bfilename bitand cardinality ceil ' +
			'chartorowid chr coalesce commit_cm compose concat convert cos cosh cube current_date ' +
			'current_time current_timestamp dbtimezone decode decompose deref dump empty_blob ' +
			'empty_clob exists exp floor from_tz glb greatest greatest_lb grouping hextoraw ' +
			'initcap instr instrb instrc instr2 instr4 isnchar last_day least least_ub length ' +
			'lengthb lengthc length2 length4 level ln localtime localtimestamp log lower lpad ltrim lub ' +
			'months_between nanvl nchartorowid nchr new_time next_day nhextoraw nls_charset_decl_len ' +
			'nls_charset_id nls_charset_name nls_initcap nls_lower nlssort nls_upper nullfn nullif ' +
			'numtodsinterval numtoyminterval nvl power rawtohex rawtonhex ref regexp_instr' +
			'sessiontimezone set_transaction_use sign sin sinh soundex sqrt substr substrb '+
			'substrc substr2 substr4 sys_at_time_zone sys_context sysdate sys_extract_utc sys_guid ' +
			'sys_literaltodate sys_literaltodsinterval sys_literaltotime sys_literaltotimestamp ' +
			'sys_literaltotztime sys_literaltotztimestamp sys_literaltoyminterval ' +
			'systimestamp tan tanh to_anylob to_binary_double to_binary_float ' +
			'to_blob to_char to_clob to_date to_dsinterval to_label to_multi_byte to_nchar to_nclob ' +
			'to_number to_raw to_single_byte to_time to_timestamp to_timestamp_tz to_time_tz ' +
			'to_yminterval translate trim trunc tz_offset uid unistr upper urowid user userenv ' +
			'value vsize xor ' +
			'regexp_like regexp_replace regexp_substr remainder replace rollback_nr rollback_sv ' +
			'rollup round rowid rowidtochar rowidtonchar rowlabel rownum rpad rtrim savepoint';

		var keywords = 'access else modify start add exclusive noaudit select exists nocompress' +
			'session alter file set float notfound share for nowait size arraylen' +
			'from smallint as grant number sqlbuf asc group of successful audit having' +
			'offline synonym identified on sysdate by immediate online table char ' +
			'option then check increment to cluster index order trigger column initial pctfree' +
			'uid comment insert prior union compress integer privileges unique connect' +
			'intersect public update create into raw user current is rename validate date' +
			'level resource values decimal revoke varchar default lock row varchar2 delete' +
			'long rowid view desc maxextents rowlabel whenever distinct minus rownum where drop' +
			'mode rows with admin cursor found mount after cycle function next allocate database' +
			'go new analyze datafile goto noarchivelog archive dba groups nocache archivelog dec' +
			'including nocycle authorization declare indicator nomaxvalue avg disable initrans'+
			'nominvalue backup dismount instance none begin double int noorder become dump key' +
			'noresetlogs before each language normal block enable layer nosort body end link' +
			'numeric cache escape lists off cancel events logfile old cascade except manage' +
			'only change exceptions manual open character exec max optimal checkpoint explain' +
			'maxdatafiles own close execute maxinstances package cobol extent maxlogfiles parallel' +
			'commit externally maxloghistory pctincrease compile fetch maxlogmembers' +
			'pctused constraint flush maxtrans plan constraints freelist maxvalue pli' +
			'contents freelists min precision continue force minextents primary controlfile' +
			'foreign minvalue private count fortran module procedure profile savepoint' +
			'sqlstate tracing quota schema statement_id transaction read scn statistics' +
			'triggers real section stop truncate recover segment storage under references' +
			'sequence sum unlimited referencing shared switch until resetlogs snapshot' +
			'system use restricted tables using reuse sort tablespace when role sql' +
			'temporary write roles sqlcode thread work rollback sqlerror time abort' +
			'crash digits accept binary_integer create dispose access body' +
			'current distinct add boolean currval do all by cursor drop alter case' +
			'database else char data_base elsif char_base date end array check' +
			'dba entry arraylen close debugoff exception as cluster debugon' +
			'exception_init asc clusters declare exists assert colauth decimal exit' +
			'assign columns default false at commit definition fetch authorization' +
			'compress delay float avg connect delete for base_table constant delta form' +
			'begin count desc from function new release sum generic nextval remr' +
			'tabauth goto nocompress rename table grant resource tables group' +
			'return task having number reverse terminate identified number_base revoke' +
			'then if of rollback to on rowid true index open rowlabel type indexes option rownum' +
			'union indicator rowtype unique insert order run update integer others savepoint use' +
			'intersect out schema values into package select varchar is partition separate' +
			'varchar2 level pctfree variance positive size view limited pragma' +
			'smallint views loop prior space when max private sql where min procedure' +
			'sqlcode while minus public sqlerrm with mlslabel raise start work mod range' +
			'statement mode real stddev natural record subtype returning exit';

		var operators =	'all and any between cross in join like not null or outer some';

		this.regexList = [
			{ regex: /--(.*)$/gm, 							css: 'comments' },			// one line comments
			{ regex: SyntaxHighlighter.regexLib.multiLineCComments,			css: 'comments' },			// multiple lines comments
			{ regex: SyntaxHighlighter.regexLib.multiLineDoubleQuotedString,	css: 'string' },			// double quoted strings
			{ regex: SyntaxHighlighter.regexLib.multiLineSingleQuotedString,	css: 'string' },			// single quoted strings
			{ regex: new RegExp(this.getKeywords(funcs), 'gmi'),			css: 'color2' },			// functions
			{ regex: new RegExp(this.getKeywords(operators), 'gmi'),		css: 'color1' },			// operators and such
			{ regex: new RegExp(this.getKeywords(keywords), 'gmi'),			css: 'keyword' }			// keyword
			];
	};

	Brush.prototype	= new SyntaxHighlighter.Highlighter();
	Brush.aliases	= ['plsql', 'pkb', 'pkh'];

	SyntaxHighlighter.brushes.PlSql = Brush;

	// CommonJS
	typeof(exports) != 'undefined' ? exports.Brush = Brush : null;
})();

