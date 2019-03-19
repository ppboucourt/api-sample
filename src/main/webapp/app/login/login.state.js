(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('login', {
            url: '/login',
            templateUrl: 'app/login/login.html',
            controller: 'LoginController',
            controllerAs: 'vm',
            data: {
                authorities: []
            },
            resolve: {}
        }).state('consent', {
            //parent: 'login',
            url: '/consent',
            templateUrl: 'app/login/consent.html',
            controller: 'SignController',
            controllerAs: 'vm',
            data: {
                authorities: []
            },
            resolve: {}
        }).state('collect-signature', {
           // parent: 'login',
            url: '/collect-signature',
            controller: 'SignController',
            controllerAs: 'vm',
            data: {
                authorities: []
            },
            templateUrl: 'app/login/signature.html',
            resolve: {}
        }).state('two-factor', {
            // parent: 'login',
            url: '/two-factor',
            controller: 'TwoFactorController',
            controllerAs: 'vm',
            data: {
                authorities: []
            },
            templateUrl: 'app/login/twofactor.html',
            resolve: {}
        });
    }
})();
