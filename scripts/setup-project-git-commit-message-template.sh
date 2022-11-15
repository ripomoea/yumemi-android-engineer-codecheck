#!/bin/sh

# カレントディレクトリを取得
FILE_PATH=$(
  cd "$(dirname "$0")" || exit
  pwd
)

# プロジェクトのルートディレクトリに移動
cd "$FILE_PATH/../" || exit

# Git のコミットメッセージテンプレートを設定
git config commit.template tools/git/.gitmessage
