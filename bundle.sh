IMPORTS=`find src -name "*.java" |
    xargs cat |
    grep '^import' |
    grep -v '^import cscc43' |
    sort |
    uniq`
SOURCE=`find src -name "*.java" |
    xargs cat |
    grep -v '^package' |
    grep -v '^import' |
    sed -e 's/^public class/class/g' |
    sed -e 's/^public interface/interface/g' |
    sed -e 's/^public @interface/@interface/g' |
    sed -e 's/^class App\b/public class App/g'`
echo "$IMPORTS"
echo "$SOURCE"
