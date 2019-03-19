(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('sign', {
                abstract: true,
                parent: '',
                url: '/sign',
                ncyBreadcrumb: {
                    label: 'Sign',
                    parent: 'login'
                },
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/sign/sign.html',
                        controller: 'SignController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'home',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })

            .state('sign.consent', {
                parent: 'sign',
                url: '/consent',
                ncyBreadcrumb: {
                    label: 'Consent',
                    parent: 'app'
                },
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    '': {
                        templateUrl: 'app/sign/consent.html'
                    }
                },
                resolve: {}
            })
            .state('sign.signature', {
                parent: 'sign',
                url: '/signature',
                ncyBreadcrumb: {
                    label: 'Signature',
                    parent: 'sign.consent'
                },
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    '': {
                        templateUrl: 'app/sign/signature.html'
                    }
                },
                resolve: {}
            })

    }
})();
