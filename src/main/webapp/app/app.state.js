(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$breadcrumbProvider', 'IdleProvider', 'IDLE'];

    function stateConfig($stateProvider, $breadcrumbProvider, IdleProvider, IDLE) {

        /**
         * Config the BreadCrumb
         */

        var breadcrumbTemplate = '<ol class="breadcrumb">' +
            '<li ng-repeat="step in steps" ng-class="{active: $last}" ng-switch="$last || !!step.abstract">' +
            '<a ng-switch-when="false" href="{{step.ncyBreadcrumbLink}}">' +
            '<i data-ng-show="$index===0" class="fa fa-dashboard"></i>{{step.ncyBreadcrumbLabel}}</a>' +
            '<span ng-switch-when="true">{{step.ncyBreadcrumbLabel}}</span>' +
            '</li>' +
            '</ol>';

        $breadcrumbProvider.setOptions({
            prefixStateName: 'home',
            // template: BreadcrumbTemplate.name
            template: breadcrumbTemplate
            // template: 'bootstrap3',
            // templateUrl: 'templates/breadcrumb.tpl.html'
        });

        $stateProvider
            .state('app', {
                abstract: true,
                views: {
                    'headBar@': {
                        templateUrl: 'app/layouts/header/header.tpl.html',
                        controller: 'HeaderController',
                        controllerAs: 'vm'
                    },
                    'sideBar@': {
                        templateUrl: 'app/layouts/sidebar/sidebar.tpl.html',
                        controller: 'SidebarController',
                        controllerAs: 'vm'
                    },
                    'footer@': {
                        templateUrl: 'app/layouts/footer/footer.tpl.html',
                        controller: 'FooterController',
                        controllerAs: 'vm'
                    },
                    'controlBar@': {
                        templateUrl: 'app/layouts/controlbar/controlsidebar.tpl.html',
                        controller: 'ControlBarController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    authorize: ['Auth',
                        function (Auth) {
                            return Auth.authorize();
                        }
                    ]
                }
            });

        IdleProvider.idle(IDLE.idle);
        IdleProvider.timeout(IDLE.timeout);
    }
})();
