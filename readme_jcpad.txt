          ------------------
          Java開発環境
          CPad for Java2 SDK
          ------------------
          Copyright(c) 1998-2001 稀杜(kito)

※機能説明などの詳細は cpad_manual.html を参照してください。

2001/10/26 Version 2.31

■概要
======
CPad for Java2 SDK（以下JCPad or CPad）は、
Sun Microsystems社の「Java2 SDK Standard Edition」用のJava開発環境です。
GUI環境で簡単にJavaプログラミングができます。

簡単に「コンパイル」＆「実行」ができるので、プログラミング初心者の
学習用途はもちろん、ちょっとしたテストコードの実行に手間を
かけたくない中上級者にとっても有用なツールとなっています。


CPad Suiteとして
「C/C++開発環境 CPad for Borland C++Compiler」
「Java開発環境 CPad for Java2 SDK」
「C#開発環境 CPad for C# .NET」
「C言語開発環境 CPad for LSI C-86」
「Fortran開発環境 CPad for Salford FTN77」
「Pascal開発環境 CPad for Free Pascal」
を公開しています。


■特長
======

・使いやすいGUI環境
・複数ファイル同時編集
・設定ファイルを自動で設定
・コンパイル環境の自動構築
・見やすい構文強調表示
・エラーを表示するメッセージウィンドウ
・柔軟な実行時ウィンドウ制御
・自動バックアップ
・文字コード自動判別
・任意コマンド実行機能
・C言語ライブラリマニュアルが使える「LSI C-86 マニュアルビューア」
・エラーメッセージからのジャンプはAntに対応
・Package対応
・独自機能コマンドバーによってAnt等の利用に対応

また、様々なコンパイラに対応したCPadが存在するため、
他の言語を気軽に試してみることもできます。


■必要なソフトウェア
====================
Sun Microsystems社の「Java2 SDK Standard Edition」が必要です。

2001/09/06 現在、以下の URL からダウンロードできます。

Java2 SDK Standard Edition
http://java.sun.com/j2se/1.3/ja/ 

※プログラミング関連の雑誌などにも収録されていることがあります。（IDG Japan「JavaWorld」等）

ダウンロード後、適当なディレクトリにインストールしてください。

※ソースが増えてきたときは Ant 等のコンパイル支援ツールを利用するとよいでしょう。


■インストール方法＆使い方
==========================
・適当なフォルダをつくり、アーカイブファイルを展開してできた
ファイルをすべてコピーしてください。
（バージョンアップ時にはすべてのファイルを上書してください。）

・ダブルクリックするなどして、"jcpad.exe"を実行してください。

・[設定]ダイアログが出てくるので、コンパイラ(javac.exe)と通常Javaの
ソースファイルを保存するフォルダのパスを設定してください。
"javac.exe"は「Java2 SDK」をインストールしたフォルダの中の
"Bin"フォルダにあります。
[標準のデータ保存フォルダ]に存在しないフォルダを指定すると、
確認後に作成します。

・[F9]キーで[コンパイル＆実行]ができます。

※CPadはファイル名をクラス名として解釈します。
ファイル名とクラス名は大文字・小文字を含めて同一にしてください。


■アンインストール方法
======================
インストール時に作成したフォルダを削除してください。
（このフォルダの中にデータフォルダを作っている場合はデータも一緒に
消えてしまいます。注意してください。）

※CPadはレジストリを使用しません。


■動作環境
==========
Windows95/98/ME/NT4.0/2000（環境によって多少動作が異なります。）


■作者から一言
==============
このソフトを使用したご感想・ご意見、またはバグ報告などを

Webページ上の掲示板
http://hp.vector.co.jp/authors/VA017148/

あるいはメール
kito@kun.ne.jp

で、作者の稀杜(kito)まで伝えていただけると幸いです。

CPadサポートは主に上記Webページ上の掲示板(BBS)で行います。
※質問等はメールを直接送ってもらうよりも、できるだけ掲示板(BBS)に書いてください。
ユーザーの皆さんで情報が共有できます。

また、作者事情により全てのメールに対して返信することができないからです。m(_ _)m


■フリーソフトウェア・著作権など
================================
CPad for Java2 SDK はフリーソフトウェアです。
CPadの著作権は私、稀杜(kito)が保持します。
転載は自由ですが、事後でもいいので作者に連絡してください。
雑誌掲載の場合は、事前に作者に連絡してください。

※転載制限はしませんが、インターネットが利用できる環境の場合は転載よりもできるだけ

きときと
http://hp.vector.co.jp/authors/VA017148/
CPadのページ
http://hp.vector.co.jp/authors/VA017148/pages/cpad.html

へのリンクを利用してください。

旧バージョンを転載したまま更新されないとユーザーにとって不利益になる場合があります。



■謝辞
======
CPadは、
Borland社の「Borland Delphi5 Professional」で作成されています。

"cpadcmd.exe"は「CPad for Borland C++Compiler」を利用して
Borland社の「Borland C++Compiler5.5」で作成されています。

は以下のDelphiコンポーネント及びツールをそのまま、または改変して使用しています。


本田勝彦氏の「TEditor1.75」および「TStringPrinter1.3」 
ヒシアマゾン氏の「SHBrowseForFolderEx Version 2.01」 
Hayato Sasaki氏の「CoolDialog」
EarthWave Soft(IKEDA Takahiro)氏の「Delphi 用 文字コード変換ライブラリユニット jconvert.pas 1.4」 
伊藤 隆志氏の「Shell Component Library for Borland Delphi5 Version 0.96」
bmonkey氏の「正規表現を使った文字列探索/操作コンポーネント集ver0.16」

良質なコンポーネントを提供していただきありがとうございました。 

加えて、 
@nifty FDELPHIフォーラム、SINPRISEフォーラムの皆さん、Delphi-MLの皆さん、

このソフトを作るきっかけになり、実はベータテスターになっていたWM君とYT君、

バグ報告＆ご要望をくださいましたユーザーの皆さん、

この場を借りてお礼申し上げます。どうもありがとうございました。


---
稀杜(kito)
Email:kito@kun.ne.jp
Web:http://hp.vector.co.jp/authors/VA017148/
