// Karma configuration
// http://karma-runner.github.io/0.13/config/configuration-file.html

var sourcePreprocessors = ['coverage'];

function isDebug() {
    return process.argv.indexOf('--debug') >= 0;
}

if (isDebug()) {
    // Disable JS minification if Karma is run with debug option.
    sourcePreprocessors = [];
}

module.exports = function (config) {
    config.set({
        // base path, that will be used to resolve files and exclude
        basePath: 'src/test/javascript/'.replace(/[^/]+/g, '..'),

        // testing framework to use (jasmine/mocha/qunit/...)
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            // bower:js
            'src/main/webapp/bower_components/jquery/dist/jquery.js',
            'src/main/webapp/bower_components/json3/lib/json3.js',
            'src/main/webapp/bower_components/messageformat/messageformat.js',
            'src/main/webapp/bower_components/datatables.net/js/jquery.dataTables.js',
            'src/main/webapp/bower_components/jSignature/src/jSignature.js',
            'src/main/webapp/bower_components/jSignature/src/plugins/jSignature.CompressorBase30.js',
            'src/main/webapp/bower_components/jSignature/src/plugins/jSignature.CompressorSVG.js',
            'src/main/webapp/bower_components/jSignature/src/plugins/jSignature.UndoButton.js',
            'src/main/webapp/bower_components/moment/moment.js',
            'src/main/webapp/bower_components/chart.js/dist/Chart.js',
            'src/main/webapp/bower_components/d3/d3.js',
            'src/main/webapp/bower_components/nvd3/build/nv.d3.js',
            'src/main/webapp/bower_components/bootstrap-daterangepicker/daterangepicker.js',
            'src/main/webapp/bower_components/clientjs/dist/client.min.js',
            'src/main/webapp/bower_components/cookies/lib/cookies.js',
            'src/main/webapp/bower_components/jquery.easing/js/jquery.easing.min.js',
            'src/main/webapp/bower_components/bootstrap-fileinput/js/fileinput.min.js',
            'src/main/webapp/bower_components/angular/angular.js',
            'src/main/webapp/bower_components/angular-aria/angular-aria.js',
            'src/main/webapp/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            'src/main/webapp/bower_components/angular-cache-buster/angular-cache-buster.js',
            'src/main/webapp/bower_components/angular-cookies/angular-cookies.js',
            'src/main/webapp/bower_components/ngstorage/ngStorage.js',
            'src/main/webapp/bower_components/angular-loading-bar/build/loading-bar.js',
            'src/main/webapp/bower_components/angular-resource/angular-resource.js',
            'src/main/webapp/bower_components/angular-sanitize/angular-sanitize.js',
            'src/main/webapp/bower_components/angular-ui-router/release/angular-ui-router.js',
            'src/main/webapp/bower_components/bootstrap-ui-datetime-picker/dist/datetime-picker.js',
            'src/main/webapp/bower_components/ng-file-upload/ng-file-upload.js',
            'src/main/webapp/bower_components/ngInfiniteScroll/build/ng-infinite-scroll.js',
            'src/main/webapp/bower_components/angular-bootstrap-checkbox/angular-bootstrap-checkbox.js',
            'src/main/webapp/bower_components/angular-breadcrumb/release/angular-breadcrumb.js',
            'src/main/webapp/bower_components/angular-object-diff/dist/angular-object-diff.js',
            'src/main/webapp/bower_components/angular-datatables/dist/angular-datatables.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/bootstrap/angular-datatables.bootstrap.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/colreorder/angular-datatables.colreorder.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/columnfilter/angular-datatables.columnfilter.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/light-columnfilter/angular-datatables.light-columnfilter.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/colvis/angular-datatables.colvis.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/fixedcolumns/angular-datatables.fixedcolumns.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/fixedheader/angular-datatables.fixedheader.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/scroller/angular-datatables.scroller.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/tabletools/angular-datatables.tabletools.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/buttons/angular-datatables.buttons.js',
            'src/main/webapp/bower_components/angular-datatables/dist/plugins/select/angular-datatables.select.js',
            'src/main/webapp/bower_components/angular-bootstrap-toggle/dist/angular-bootstrap-toggle.min.js',
            'src/main/webapp/bower_components/ng-ckeditor/ng-ckeditor.js',
            'src/main/webapp/bower_components/angular-ui-select/dist/select.js',
            'src/main/webapp/bower_components/angular-moment/angular-moment.js',
            'src/main/webapp/bower_components/nz-toggle/dist/nz-toggle.js',
            'src/main/webapp/bower_components/np-autocomplete/src/np-autocomplete.js',
            'src/main/webapp/bower_components/aa-angular-form-gen/dist/angular-form-gen.js',
            'src/main/webapp/bower_components/angular-ui-mask/dist/mask.js',
            'src/main/webapp/bower_components/angular-chart.js/dist/angular-chart.js',
            'src/main/webapp/bower_components/angular-nvd3/dist/angular-nvd3.js',
            'src/main/webapp/bower_components/rm-datepicker/dist/rm-datepicker.js',
            'src/main/webapp/bower_components/ng-idle/angular-idle.js',
            'src/main/webapp/bower_components/angular-animate/angular-animate.js',
            'src/main/webapp/bower_components/ng-lodash/build/ng-lodash.js',
            'src/main/webapp/bower_components/fancybox-plus/src/jquery.fancybox-plus.js',
            'src/main/webapp/bower_components/angular-base64/angular-base64.js',
            'src/main/webapp/bower_components/angular-bootstrap-fileinput/angular-bootstrap-fileinput.js',
            'src/main/webapp/bower_components/angular-validation/dist/angular-validation.js',
            'src/main/webapp/bower_components/angular-daterangepicker/js/angular-daterangepicker.js',
            'src/main/webapp/bower_components/angular-drag-and-drop-lists/angular-drag-and-drop-lists.js',
            'src/main/webapp/bower_components/angular-toastr/dist/angular-toastr.tpls.js',
            'src/main/webapp/bower_components/angular-toArrayFilter/toArrayFilter.js',
            'src/main/webapp/bower_components/angular-mocks/angular-mocks.js',
            'src/main/webapp/bower_components/angular-fancybox-plus/js/angular-fancybox-plus.js',
            // endbower
            'src/main/webapp/app/app.module.js',
            'src/main/webapp/app/app.state.js',
            'src/main/webapp/app/app.constants.js',
            'src/main/webapp/app/**/*.+(js|html)',
            'src/test/javascript/spec/helpers/module.js',
            'src/test/javascript/spec/helpers/httpBackend.js',
            'src/test/javascript/**/!(karma.conf).js'
        ],


        // list of files / patterns to exclude
        exclude: [],

        preprocessors: {
            './**/*.js': sourcePreprocessors
        },

        reporters: ['dots', 'junit', 'coverage', 'progress'],

        junitReporter: {
            outputFile: '../target/test-results/karma/TESTS-results.xml'
        },

        coverageReporter: {
            dir: 'target/test-results/coverage',
            reporters: [
                {type: 'lcov', subdir: 'report-lcov'}
            ]
        },

        // web server port
        port: 9876,

        // level of logging
        // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: false,

        // Start these browsers, currently available:
        // - Chrome
        // - ChromeCanary
        // - Firefox
        // - Opera
        // - Safari (only Mac)
        // - PhantomJS
        // - IE (only Windows)
        browsers: ['PhantomJS'],

        // Continuous Integration mode
        // if true, it capture browsers, run tests and exit
        singleRun: false,

        // to avoid DISCONNECTED messages when connecting to slow virtual machines
        browserDisconnectTimeout: 10000, // default 2000
        browserDisconnectTolerance: 1, // default 0
        browserNoActivityTimeout: 4 * 60 * 1000 //default 10000
    });
};
