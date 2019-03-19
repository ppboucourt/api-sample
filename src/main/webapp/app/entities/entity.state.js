(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('entity', {
            abstract: true,
            parent: 'app',
            ncyBreadcrumb: {
                label: 'Entity',
                parent: 'app'
            },
        });
    }
})();
