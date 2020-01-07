#!/bin/bash

set -e

gem install fastlane
fastlane supply --package_name com.g2pdev.smartrate.demo --apk "${TRAVIS_BUILD_DIR}"/app/build/outputs/apk/release/app-release.apk --track alpha --json_key "${TRAVIS_BUILD_DIR}"/scripts/google-play-auth.json
