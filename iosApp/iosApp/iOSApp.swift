// Created by Lexter Labra

import ComposeApp
import SwiftUI

@main
struct IOSApp: App {
    init() {
        KoinKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
