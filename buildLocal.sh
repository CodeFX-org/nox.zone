if [ -z $1 ]; then
	echo "Please provide a target path"
else
	export SDKMAN_DIR="$HOME/.sdkman"
	[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"
	sdk offline
	sdk use java 23
	mvn clean package -DskipTests --quiet
	rm -rf $1/nox.zone
	java --enable-preview -cp "app/*" zone.nox.Main --target local --siteFolder $1/nox.zone
fi
