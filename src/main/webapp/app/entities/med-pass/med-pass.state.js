(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('med-pass', {
            parent: 'entity',
            url: '/med-pass',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Med Pass'
            },
            ncyBreadcrumb: {
                label: 'Med Pass'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/med-pass/med-pass.html',
                    controller: 'MedPassController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }

})();
