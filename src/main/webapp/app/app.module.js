(function () {
    'use strict';

    angular
        .module('bluebookApp', [
            'ngStorage',
            // 'tmh.dynamicLocale',
            // 'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            'ds.objectDiff',
            'udt',
            'datatables',
            'datatables.factory',
            'datatables.bootstrap',
            'datatables.colreorder',
            'datatables.columnfilter',

            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            // 'ui.grid',
            // 'ui.grid.autoResize',
            // 'ui.grid.selection',
            // 'ui.grid.exporter',
            'ui.checkbox',
            'ncy-angular-breadcrumb',
            'ui.toggle',
            'ngCkeditor',
            'ui.select',
            'angularMoment',
            'nzToggle',
            'ng-pros.directive.autocomplete',
            'ngSanitize',
            'fg',
            'ui.mask',
            'rmDatepicker',
            'chart.js',
            'nvd3',
            'ngIdle',
            'ngAnimate',
            // 'ngToast', //Remove later
            'toastr',
            'ngLodash',
            'fancyboxplus',
            'angularBootstrapFileinput',
            'validation', 'validation.rule',
            'daterangepicker',
            'dndLists',
            'angular-toArrayFilter',
            'ui.pagination',
        ])
        .config(['$validationProvider', '$uibTooltipProvider', 'toastrConfig',
            function ($validationProvider, $uibTooltipProvider, toastrConfig) {
                $uibTooltipProvider.options({appendToBody: true});
                //---------- Toast config ---------------------------
                angular.extend(toastrConfig, {
                    allowHtml: true,
                    closeButton: true,
                    closeHtml: '<button>&times;</button>',
                    extendedTimeOut: 1000,
                    autoDismiss: false,
                    maxOpened: 5,
                    iconClasses: {
                        error: 'toast-error',
                        info: 'toast-info',
                        success: 'toast-success',
                        warning: 'toast-warning'
                    },
                    messageClass: 'toast-message',
                    onHidden: null,
                    onShown: null,
                    onTap: null,
                    progressBar: true,
                    tapToDismiss: true,
                    newestOnTop: true,
                    positionClass: 'toast-bottom-right',
                    timeOut: 5000,
                    titleClass: 'toast-title',
                    toastClass: 'toast'
                });

                //----------- Validators config ---------------------------
                /**
                 * Setup a default messages
                 */
                var defaultMsgs = {
                    required: {
                        error: 'This field is required.',
                        success: ''
                    }
                };

                $validationProvider.setDefaultMsg(defaultMsgs);

                angular.extend($validationProvider, {
                    validCallback: function (element) {
                        //$(element).closest('div.form-group').find('label.has-error').hide();
                        $(element).parents('.form-group:first').removeClass('has-error');
                    },
                    invalidCallback: function (element) {
                        //Mover error para el tag con class form-group
                        $(element).parents('.form-group:first').addClass('has-error');
                    }
                });

                $validationProvider.setErrorHTML(function (msg) {
                    return '<p class="control-label pull-left has-error">' + msg + '</p>';
                });
                $validationProvider.showSuccessMessage = false;
                //-------------------------------------------------------

            }])
        .run(run);

    run.$inject = ['stateHandler', '$rootScope'];

    function run(stateHandler, $rootScope) {
        stateHandler.initialize();

        $rootScope.inputDateFormat = "MM/dd/yyyy";
    }
})();
