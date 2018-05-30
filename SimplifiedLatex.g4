grammar SimplifiedLatex;

/*
 * Assignments in tokens{} are not supported in ANTLR 4;
 * use lexical rule 'TOKEN:token' instead

tokens {
	TOKEN = 'token';
}
*/

/*
 * Headers must be at the beginning of the grammar.
 * There's also the tag @header but only defines the
 * header of the parser.
 */
@lexer::header {
package generated;
}
@parser::header {
package generated;
}

/*
 * Lexer aims to recognise single elements (sequence of related 
 * terminal symbols, namely characters, called tokens).
 * Rules must start with uppercase  letter and can be recursive
 * (but NOT left-recursive).
 */
 
/* Reserved words of the language */
ARTICLE: 	'article';
AUTHOR:		'author';
CV:			'cv';
END:		'end';
LETTER:		'letter';
RULE:		'rule';
TEMPLATE:	'template';
THESIS:		'thesis';
TITLE:		'title';

/* Generic words and special characters */
EOL:		('\n' '\r'?);
PERCENT:	'%';
WORD: 		([!-$] | [&-Z] | [a-z] | [¡-ÿ])+;
WS:			(' ' | '\t')+;

/*
 * Parser aims to  recognize  some  structured sequences (which
 * ones are syntactically and, hopefully, semantically correct)
 * of symbols (terminal and  non-terminal, namely other parsing
 * rules).
 * Rules must start with lowercase letter and can't be recursive.
 */
axiom:		template preamble? content END;
preamble:	(author? title) | (title? author);
title:		TITLE line;
author:		AUTHOR line;
template:	TEMPLATE WS (article | custom | cv | letter | thesis) EOL;
article:	ARTICLE;
custom:		WORD;
cv:			CV;
letter:		LETTER;
thesis:		THESIS;

/* Auxiliary parsing rules */
comment:	(WS? PERCENT line)+;
content:	(comment | customRule | text)+;
line:		(WORD | WS)+ EOL;
customRule:	WS? RULE line;
text:		line+;