#!/bin/bash

set -e

# Build APK

./gradlew :app:assembleRelease

# Sign & verify

jarsigner -verbose \
  -sigalg SHA1withRSA \
  -storepass "$storepass" \
  -keypass "$keypass" \
  -digestalg SHA1 \
  -keystore keystore.jks \
  "${RELEASE_APK_DIR}"/app-release-unsigned.apk smartrate

jarsigner -verify "${RELEASE_APK_DIR}"/app-release-unsigned.apk

# Zipalign

"${ANDROID_HOME}"/build-tools/"${ANDROID_BUILD_TOOLS}"/zipalign \
  -v 4 \
  "${RELEASE_APK_DIR}"/app-release-unsigned.apk \
  ""${RELEASE_APK_DIR}"/app-release.apk
