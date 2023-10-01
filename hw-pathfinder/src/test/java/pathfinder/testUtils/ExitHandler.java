/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.testUtils;

import java.security.Permission;

/**
 * DO NOT MODIFY
 * <p>
 * You shouldn't modify or move this class at all. It exists to allow JUnit
 * tests to call methods that might System.exit() without causing JUnit or
 * Gradle to crash. In general, System.exit() should never be called during
 * a test - it means something is very wrong. However, sometimes it makes sense
 * to have it in your main code that might be tested. If it ever gets called,
 * however, the test should fail.
 * <p>
 * Without this class in place, calling System.exit() will cause the entire
 * testing system to shut down without running any other tests. We definitely
 * don't want this. Instead, we want just that one test to fail. We do that
 * by throwing an exception - which the JUnit system will catch and fail without
 * crashing completely.
 */
@SuppressWarnings({"deprecated", "removal"})
public class ExitHandler extends SecurityManager {

    /*
     This security manager is installed into all gradle test executor JVMs
     as a global security manager. Since gradle forks a copy (or copies) of the JVM
     for running tests, it can actually be installed through a command line
     argument for the JVM as specified in build.gradle
     */

    @Override
    public void checkPermission(Permission perm) {
        // allow all
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        // allow all
    }

    @Override
    public void checkExit(int status) {
        for(Class clazz : this.getClassContext()) {
            // We only really want to affect classes being run/called by JUnit.
            // Therefore, JUnit will be on the current call stack, which is what we're checking here.
            // Other uses of System.exit (such as by the gradle test executor code) should be allowed.
            //
            // Alternatively, we could only install this security manager during the testing code,
            // such as with @BeforeClass and @AfterClass, but that doesn't easily (or automatically)
            // integrate itself into classes that are written by students.
            if(clazz.getName().contains("junit")) {
                throw new RuntimeException("This test attempted to System.exit() - this is not allowed");
            }
        }
        // Allow the exit implicitly by returning.
    }
}