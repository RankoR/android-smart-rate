#!/bin/bash

gem install fastlane
fastlane supply --package_name com.g2pdev.smartrate.demo --apk app/build/outputs/apk/release/app-release.apk --track alpha --json_key scripts/google-play-auth.json