<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Untitled2.c</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="generator" content="SynEdit HTML exporter" />
<style type="text/css">
<!--
body { color: #000000; background-color: #FFFFFF; }
.cpp1-assembler { color: #0000FF; }
.cpp1-character { color: #000000; }
.cpp1-comment { color: #3399FF; font-style: italic; }
.cpp1-float { color: #800080; }
.cpp1-hexadecimal { color: #800080; }
.cpp1-identifier { color: #000000; }
.cpp1-illegalchar { color: #000000; }
.cpp1-number { color: #800080; }
.cpp1-octal { color: #800080; }
.cpp1-preprocessor { color: #008000; }
.cpp1-reservedword { color: #000000; font-weight: bold; }
.cpp1-space { background-color: #FFFFFF; color: #000000; }
.cpp1-string { color: #0000FF; font-weight: bold; }
.cpp1-symbol { color: #FF0000; font-weight: bold; }
-->
</style>
</head>
<body>
<pre>
<code><span style="font: 10pt Courier New;"><span class="cpp1-preprocessor">#include&lt;stdio.h&gt;
</span><span class="cpp1-reservedword">int</span><span class="cpp1-space"> main</span><span class="cpp1-symbol">()
{
</span><span class="cpp1-reservedword">int</span><span class="cpp1-space"> numero1</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> numero2</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> num1</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> num2</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> pontos</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> vida</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> danos</span><span class="cpp1-symbol">;
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;pontos de vida: &quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">scanf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;%d&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> </span><span class="cpp1-symbol">&amp;</span><span class="cpp1-identifier">vida</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;ATK igual a\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">scanf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;%d&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> </span><span class="cpp1-symbol">&amp;</span><span class="cpp1-identifier">numero1</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;ATK igual a\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">scanf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;%d&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> </span><span class="cpp1-symbol">&amp;</span><span class="cpp1-identifier">numero2</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;estatos no jogo:\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">numero1</span><span class="cpp1-symbol">&gt;</span><span class="cpp1-identifier">numero2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">danos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> numero1 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> numero2</span><span class="cpp1-symbol">;
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">numero1</span><span class="cpp1-symbol">&gt;</span><span class="cpp1-identifier">numero2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">pontos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> vida </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">numero1 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> numero2</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">numero1</span><span class="cpp1-symbol">&lt;</span><span class="cpp1-identifier">numero2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">danos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> numero2 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> numero1</span><span class="cpp1-symbol">;
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">numero1</span><span class="cpp1-symbol">&lt;</span><span class="cpp1-identifier">numero2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">pontos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> vida </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> danos</span><span class="cpp1-symbol">;
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;pontos de vida atuais igual a: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> vida</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;danos recebidos: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> danos</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;pontos de vida restantes: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> pontos</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">pontos</span><span class="cpp1-symbol">&lt;=</span><span class="cpp1-number">0</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;fim de jogo: \npontos de vida restantes: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> pontos</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">numero1</span><span class="cpp1-symbol">==</span><span class="cpp1-identifier">numero2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;Empate: \nambos foram destruidos:\n&Atilde;&pound;o ouve danos\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">pontos</span><span class="cpp1-symbol">&lt;=</span><span class="cpp1-number">0</span><span class="cpp1-symbol">)
</span><span class="cpp1-reservedword">return</span><span class="cpp1-space"> </span><span class="cpp1-number">0</span><span class="cpp1-symbol">;
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">pontos</span><span class="cpp1-symbol">&gt;</span><span class="cpp1-number">0</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;continue o jogo: \n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;pontos de vida atuais: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> pontos</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;ATK igual a\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">scanf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;%d&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> </span><span class="cpp1-symbol">&amp;</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;ATK igual a\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">scanf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;%d&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> </span><span class="cpp1-symbol">&amp;</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;novos estatos no jogo:\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">&gt;</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">danos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> num1 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> num2</span><span class="cpp1-symbol">;
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">&gt;</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">pontos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> vida </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">numero1 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> numero2</span><span class="cpp1-symbol">)-((</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">-</span><span class="cpp1-space"> num2</span><span class="cpp1-symbol">));
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">&lt;</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">danos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> num2 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> num1</span><span class="cpp1-symbol">;
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">&lt;</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">pontos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> vida </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">numero2 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> numero1</span><span class="cpp1-symbol">)-((</span><span class="cpp1-identifier">danos</span><span class="cpp1-symbol">));
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;pontos de vida igual a: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> pontos</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;danos recebidos: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> danos</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;pontos de vida restantes: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> pontos</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">pontos</span><span class="cpp1-symbol">&lt;=</span><span class="cpp1-number">0</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;fim de jogo: \npontos de vida restantes: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> pontos</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">==</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;Empate: \nambos foram destruidos:\n n&Atilde;&pound;o ouve danos\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">pontos</span><span class="cpp1-symbol">&lt;=</span><span class="cpp1-number">0</span><span class="cpp1-symbol">)
</span><span class="cpp1-reservedword">return</span><span class="cpp1-space"> </span><span class="cpp1-number">0</span><span class="cpp1-symbol">;
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">pontos</span><span class="cpp1-symbol">&gt;</span><span class="cpp1-number">0</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;continue o jogo: \n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;pontos de vida atuais: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> pontos</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;ATK igual a\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">scanf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;%d&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> </span><span class="cpp1-symbol">&amp;</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;ATK igual a\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">scanf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;%d&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> </span><span class="cpp1-symbol">&amp;</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;novos estatos no jogo:\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">&gt;</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">danos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> num1 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> num2</span><span class="cpp1-symbol">;
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">&gt;</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">pontos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> vida </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">numero1 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> numero2</span><span class="cpp1-symbol">)-((</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">-</span><span class="cpp1-space"> num2</span><span class="cpp1-symbol">));
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">&lt;</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">danos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> num2 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> num1</span><span class="cpp1-symbol">;
</span><span class="cpp1-reservedword">if</span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">&lt;</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">pontos </span><span class="cpp1-symbol">=</span><span class="cpp1-space"> vida </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">numero2 </span><span class="cpp1-symbol">-</span><span class="cpp1-space"> numero1</span><span class="cpp1-symbol">)-((</span><span class="cpp1-identifier">danos</span><span class="cpp1-symbol">));
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;pontos igual a: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> vida</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;danos recebidos: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> danos</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;pontos de vida restantes: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> pontos</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">num1</span><span class="cpp1-symbol">==</span><span class="cpp1-identifier">num2</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;Empate: \nambos foram destruidos: \n n&Atilde;&pound;o ouve danos\n&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">if</span><span class="cpp1-space"> </span><span class="cpp1-symbol">(</span><span class="cpp1-identifier">pontos</span><span class="cpp1-symbol">&lt;=</span><span class="cpp1-number">0</span><span class="cpp1-symbol">)
</span><span class="cpp1-identifier">printf</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;fim de jogo: \npontos de vida restantes: %d\n&quot;</span><span class="cpp1-symbol">,</span><span class="cpp1-space"> pontos</span><span class="cpp1-symbol">);
</span><span class="cpp1-identifier">system</span><span class="cpp1-symbol">(</span><span class="cpp1-string">&quot;pause&quot;</span><span class="cpp1-symbol">);
</span><span class="cpp1-reservedword">return</span><span class="cpp1-space"> </span><span class="cpp1-number">0</span><span class="cpp1-symbol">;
}

</span></span>
</code></pre>
</body>
</html>