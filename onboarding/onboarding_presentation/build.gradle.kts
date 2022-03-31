apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    //because i need dimensions localspacing so i implement coreUi Module where it was defined
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.onboardingDomain))
}