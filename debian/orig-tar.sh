#!/bin/bash

echo "Exporting latest Jemmy2 from svn"

address=https://svn.java.net/svn/jemmy~svn/trunk/AWT/Jemmy2
version_file=src/org/netbeans/jemmy/version_info

mkdir Jemmy2
cd Jemmy2
svn -q export $address/build.xml
svn -q export $address/nbproject
svn -q export $address/src

major=$(awk '$1=="Jemmy-MajorVersion:" {print $2}' $version_file)
minor=$(awk '$1=="Jemmy-MinorVersion:" {print $2}' $version_file)

echo "Found version $major.$minor. Creating tarball."
cd ../

tar -czf ../libjemmy2-java_$major.$minor.orig.tar.gz Jemmy2

rm -rf Jemmy2

echo "Done."
