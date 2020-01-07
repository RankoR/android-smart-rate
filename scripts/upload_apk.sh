#!/bin/bash

set -e

gem install fastlane

ls -la "${RELEASE_APK_DIR}/"

fastlane supply \
  --package_name com.g2pdev.smartrate.demo \
  --apk "${RELEASE_APK_DIR}"/app-release.apk \
  --track alpha \
  --skip_upload_metadata true \
  --skip_upload_changelogs true \
  --skip_upload_images true \
  --skip_upload_screenshots true \
  --json_key "${TRAVIS_BUILD_DIR}"/scripts/google-play-auth.json
