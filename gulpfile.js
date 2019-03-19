// Generated on 2016-11-21 using generator-jhipster 3.11.0
'use strict';

var gulp = require('gulp'),
    rev = require('gulp-rev'),
    templateCache = require('gulp-angular-templatecache'),
    htmlmin = require('gulp-htmlmin'),
    imagemin = require('gulp-imagemin'),
    ngConstant = require('gulp-ng-constant'),
    rename = require('gulp-rename'),
    eslint = require('gulp-eslint'),
    del = require('del'),
    runSequence = require('run-sequence'),
    browserSync = require('browser-sync'),
    KarmaServer = require('karma').Server,
    plumber = require('gulp-plumber'),
    changed = require('gulp-changed'),
    gulpIf = require('gulp-if'),
    moment = require('moment'),
    scss = require('gulp-sass'),
    autoprefixer = require('gulp-autoprefixer'),
    sourcemap = require('gulp-sourcemaps');

var handleErrors = require('./gulp/handle-errors'),
    serve = require('./gulp/serve'),
    util = require('./gulp/utils'),
    copy = require('./gulp/copy'),
    inject = require('./gulp/inject'),
    build = require('./gulp/build');

var config = require('./gulp/config');

gulp.task('clean', function () {
    return del([config.dist], {dot: true});
});

gulp.task('copy', ['copy:fonts', 'copy:common']);

gulp.task('copy:fonts', copy.fonts);

gulp.task('copy:common', copy.common);

gulp.task('copy:swagger', copy.swagger);

gulp.task('copy:images', copy.images);

gulp.task('images', function () {
    return gulp.src(config.app + 'content/images/**')
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(changed(config.dist + 'content/images'))
        .pipe(imagemin({optimizationLevel: 5, progressive: true, interlaced: true}))
        .pipe(rev())
        .pipe(gulp.dest(config.dist + 'content/images'))
        .pipe(rev.manifest(config.revManifest, {
            base: config.dist,
            merge: true
        }))
        .pipe(gulp.dest(config.dist))
        .pipe(browserSync.reload({stream: true}));
});

//Task to process Sass files in the 'scss' folder
gulp.task('scss', function () {
    return gulp.src(config.app + '/content/css/scss/**/*.scss')
        .pipe(sourcemap.init())
        .pipe(scss({outputStyle: 'expanded'}).on('error', scss.logError))
        .pipe(autoprefixer(autoprefixer({
            browsers : ['> 1%', 'last 5 versions', 'ie > 10']
        })))
        .pipe(sourcemap.write('.'))
        .pipe(gulp.dest(config.app + '/content/css/'))
        .pipe(browserSync.stream());
});

//watcher task for the scss files
gulp.task('scss:watch', function () {
    gulp.watch(config.app + '/content/css/scss/**/*.scss', ['scss']);
});

gulp.task('styles', [], function () {
    return gulp.src(config.app + 'content/css')
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('inject', function () {
    runSequence('inject:dep', 'inject:app');
});

gulp.task('inject:dep', ['inject:test', 'inject:vendor']);

gulp.task('inject:app', inject.app);

gulp.task('inject:vendor', inject.vendor);

gulp.task('inject:test', inject.test);

gulp.task('inject:troubleshoot', inject.troubleshoot);

gulp.task('adminLTE', function () {
    return gulp.src(config.app + 'content/adminLTE/**')
        .pipe(gulp.dest(config.dist + 'content/adminLTE'))
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('fontawesome', function () {
    return gulp.src(config.app + 'bower_components/font-awesome/fonts/*.*')
        .pipe(gulp.dest(config.dist + 'content/fonts'));
});

gulp.task('ionicons', function () {
    return gulp.src(config.app + 'bower_components/ionicons/fonts**/*.{woff,woff2,svg,ttf,eot,otf}')
        .pipe(gulp.dest(config.dist + 'content'))
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('dymo', function () {
    return gulp.src(config.app + 'content/dymo/**')
        .pipe(gulp.dest(config.dist + 'content/dymo'))
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('assets:prod', ['adminLTE', 'fontawesome', 'ionicons', 'dymo', 'images', 'styles', 'html', 'copy:swagger', 'copy:images'], build);

gulp.task('html', function () {
    return gulp.src(config.app + 'app/**/*.html')
        .pipe(htmlmin({collapseWhitespace: true}))
        .pipe(templateCache({
            module: 'bluebookApp',
            root: 'app/',
            moduleSystem: 'IIFE'
        }))
        .pipe(gulp.dest(config.tmp));
});

gulp.task('ngconstant:dev', function () {
    return ngConstant({
        name: 'bluebookApp',
        merge: true,
        constants: {
            VERSION: util.parseVersion(),
            DEBUG_INFO_ENABLED: true,
            ROLES: {
                ROLE_ADMIN: 'ROLE_ADMIN',
                ROLE_BHT: "ROLE_BHT",
                ROLE_CASE_MANAGER: "ROLE_CASE_MANAGER",
                ROLE_CLINICAL_DIRECTOR: "ROLE_CLINICAL_DIRECTOR",
                ROLE_DIRECTOR_NURSE: "ROLE_DIRECTOR_NURSE",
                ROLE_LAB: "ROLE_LAB",
                ROLE_LICENSE_PRACTITIONER_NURSE: "ROLE_LICENSE_PRACTITIONER_NURSE",
                ROLE_MD: "ROLE_MD",
                ROLE_OTHER_THERAPIST: "ROLE_OTHER_THERAPIST",
                ROLE_PHYSICIAN_ASSISTANCE: "ROLE_PHYSICIAN_ASSISTANCE",
                ROLE_PRIMARY_THERAPIST: "ROLE_PRIMARY_THERAPIST",
                ROLE_PROGRAM_DIRECTOR: "ROLE_PROGRAM_DIRECTOR",
                ROLE_REGISTER_NURSE: "ROLE_REGISTER_NURSE",
                ROLE_SUPER_ADMIN: "ROLE_SUPER_ADMIN",
                ROLE_USER: "ROLE_USER"

            },
            APP: {
                APP_NAME: 'Bluebook',
                APP_SUFFIX_NAME: 'X',
                APP_SHORT_NAME: 'DX'
            },
            TAB: {CONCURRENT_REVIEW: 15},
            GROUP_SESSION_DETAIL_PROGRESS: {
                IN_PROCESS: 'InProcess',
                PENDING_REVIEW: 'PendingReview',
                COMPLETED: 'Completed'
            },
            DATA: {
                SIGNATORIES: {
                    GUARANTOR: 'GUARANTOR',
                    PATIENT: 'PATIENT'
                },
                RELATIONSHIP: {
                    GUARANTOR: 'Guarantor',
                    GUARANTOR_ID: 5

                }
            },
            IDLE: {
                idle: 870,
                timeout: 30
            },
            FREQUENCIES: {
                WEEK_DAYS_ONLY: 'Week_Days_Only',
                DAILY: 'Daily',
                THREE_DAYS: 'ThreeDays',
                SESSIONS: 'Sessions'
            }

        },
        template: config.constantTemplate,
        stream: true
    })
        .pipe(rename('app.constants.js'))
        .pipe(gulp.dest(config.app + 'app/'));
});

gulp.task('ngconstant:prod', function () {
    return ngConstant({
        name: 'bluebookApp',
        constants: {
            VERSION: util.parseVersion(),
            DEBUG_INFO_ENABLED: false,
            ROLES: {
                ROLE_ADMIN: 'ROLE_ADMIN',
                ROLE_BHT: "ROLE_BHT",
                ROLE_CASE_MANAGER: "ROLE_CASE_MANAGER",
                ROLE_CLINICAL_DIRECTOR: "ROLE_CLINICAL_DIRECTOR",
                ROLE_DIRECTOR_NURSE: "ROLE_DIRECTOR_NURSE",
                ROLE_LAB: "ROLE_LAB",
                ROLE_LICENSE_PRACTITIONER_NURSE: "ROLE_LICENSE_PRACTITIONER_NURSE",
                ROLE_MD: "ROLE_MD",
                ROLE_OTHER_THERAPIST: "ROLE_OTHER_THERAPIST",
                ROLE_PHYSICIAN_ASSISTANCE: "ROLE_PHYSICIAN_ASSISTANCE",
                ROLE_PRIMARY_THERAPIST: "ROLE_PRIMARY_THERAPIST",
                ROLE_PROGRAM_DIRECTOR: "ROLE_PROGRAM_DIRECTOR",
                ROLE_REGISTER_NURSE: "ROLE_REGISTER_NURSE",
                ROLE_SUPER_ADMIN: "ROLE_SUPER_ADMIN",
                ROLE_USER: "ROLE_USER"
            },
            APP: {
                APP_NAME: 'Bluebook',
                APP_SUFFIX_NAME: 'X',
                APP_SHORT_NAME: 'DX'
            },
            TAB: {
                CONCURRENT_REVIEW: 15
            },
            GROUP_SESSION_DETAIL_PROGRESS: {
                IN_PROCESS: 'InProcess',
                PENDING_REVIEW: 'PendingReview',
                COMPLETED: 'Completed'
            },
            DATA: {
                SIGNATORIES: {
                    GUARANTOR: 'GUARANTOR',
                    PATIENT: 'PATIENT'
                },
                RELATIONSHIP: {
                    GUARANTOR: 'Guarantor',
                    GUARANTOR_ID: 5

                }
            },
            IDLE: {
                idle: 870,
                timeout: 30
            },
            FREQUENCIES: {
                WEEK_DAYS_ONLY: 'Week_Days_Only',
                DAILY: 'Daily',
                THREE_DAYS: 'ThreeDays',
                SESSIONS: 'Sessions'
            },
            SERVICES: {
                CHART: 'Chart',
                PATIENT: 'Patient'
            }
        },
        template: config.constantTemplate,
        stream: true
    })
        .pipe(rename('app.constants.js'))
        .pipe(gulp.dest(config.app + 'app/'));
});

// check app for eslint errors
gulp.task('eslint', function () {
    return gulp.src(['gulpfile.js', config.app + 'app/**/*.js'])
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(eslint())
        .pipe(eslint.format())
        .pipe(eslint.failOnError());
});

// check app for eslint errors anf fix some of them
gulp.task('eslint:fix', function () {
    return gulp.src(config.app + 'app/**/*.js')
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(eslint({
            fix: true
        }))
        .pipe(eslint.format())
        .pipe(gulpIf(util.isLintFixed, gulp.dest(config.app + 'app')));
});

gulp.task('test', ['inject:test', 'ngconstant:dev'], function (done) {
    new KarmaServer({
        configFile: __dirname + '/' + config.test + 'karma.conf.js',
        singleRun: true
    }, done).start();
});

gulp.task('watch', function () {
    gulp.watch('bower.json', ['install']);
    gulp.watch(['gulpfile.js', 'pom.xml'], ['ngconstant:dev']);
    gulp.watch(config.app + 'content/css/**/*.css', ['styles']);
    gulp.watch(config.app + 'content/images/**', ['images']);
    gulp.watch(config.app + 'app/**/*.js', ['inject:app']);
    gulp.watch([config.app + '*.html', config.app + 'app/**', config.app + 'i18n/**']).on('change', browserSync.reload);
});

gulp.task('install', function () {
    runSequence(['inject:dep', 'ngconstant:dev'], 'inject:app', 'inject:troubleshoot');
});

gulp.task('serve', ['install'], serve);

gulp.task('build', ['clean'], function (cb) {
    runSequence(['copy', 'inject:vendor', 'ngconstant:prod'], 'inject:app', 'inject:troubleshoot', 'assets:prod', cb);
});

gulp.task('default', ['serve']);
