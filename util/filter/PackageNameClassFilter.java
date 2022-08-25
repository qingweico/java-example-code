package util.filter;

/**
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 */
public class PackageNameClassFilter implements ClassFilter {

    private final String packageName;
    private final boolean includedSubPackages;
    private final String subPackageNamePrefix;

    /**
     * Constructor
     *
     * @param packageName         the name of package
     * @param includedSubPackages included sub-packages
     */
    public PackageNameClassFilter(String packageName, boolean includedSubPackages) {
        this.packageName = packageName;
        this.includedSubPackages = includedSubPackages;
        this.subPackageNamePrefix = includedSubPackages ? packageName + "." : null;
    }

    @Override
    public boolean accept(Class<?> filteredObject) {
        Package pkg = filteredObject.getPackage();
        String packageName = pkg.getName();
        boolean accepted = packageName.equals(this.packageName);
        if (!accepted && includedSubPackages) {
            accepted = packageName.startsWith(subPackageNamePrefix);
        }
        return accepted;
    }
}
