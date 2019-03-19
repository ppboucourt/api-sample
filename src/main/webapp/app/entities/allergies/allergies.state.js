(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        // .state('allergies', {
        //     parent: 'entity',
        //     url: '/allergies',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'Allergies'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/allergies/allergies.html',
        //             controller: 'AllergiesController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //     }
        // })
        // .state('allergies-detail', {
        //     parent: 'entity',
        //     url: '/allergies/{id}',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'Allergies'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/allergies/allergies-detail.html',
        //             controller: 'AllergiesDetailController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //         entity: ['$stateParams', 'Allergies', function($stateParams, Allergies) {
        //             return Allergies.get({id : $stateParams.id}).$promise;
        //         }],
        //         previousState: ["$state", function ($state) {
        //             var currentStateData = {
        //                 name: $state.current.name || 'allergies',
        //                 params: $state.params,
        //                 url: $state.href($state.current.name, $state.params)
        //             };
        //             return currentStateData;
        //         }]
        //     }
        // })
        // .state('allergies-detail.edit', {
        //     parent: 'allergies-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/allergies/allergies-dialog.html',
        //             controller: 'AllergiesDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['Allergies', function(Allergies) {
        //                     return Allergies.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('allergies.new', {
        //     parent: 'allergies',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/allergies/allergies-dialog.html',
        //             controller: 'AllergiesDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         allergen: null,
        //                         allergen_type: null,
        //                         reaction: null,
        //                         treatment: null,
        //                         status: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('allergies', null, { reload: 'allergies' });
        //         }, function() {
        //             $state.go('allergies');
        //         });
        //     }]
        // })
        // .state('allergies.edit', {
        //     parent: 'allergies',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/allergies/allergies-dialog.html',
        //             controller: 'AllergiesDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['Allergies', function(Allergies) {
        //                     return Allergies.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('allergies', null, { reload: 'allergies' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('allergies.delete', {
        //     parent: 'allergies',
        //     url: '/{id}/delete',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/allergies/allergies-delete-dialog.html',
        //             controller: 'AllergiesDeleteController',
        //             controllerAs: 'vm',
        //             size: 'md',
        //             resolve: {
        //                 entity: ['Allergies', function(Allergies) {
        //                     return Allergies.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('allergies', null, { reload: 'allergies' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // });

            .state('allergies-detail', {
                parent: 'entity',
                url: '/allergies/{aid}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Allergies'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/allergies/allergies-detail.html',
                        controller: 'AllergiesDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Allergies', function ($stateParams, Allergies) {
                        return Allergies.get({id: $stateParams.aid}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'allergies',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            // .state('allergies-new', {
            //     parent: 'patient',
            //     url: '/alnew',
            //     data: {
            //         authorities: ['ROLE_USER']
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', 'chart', function ($stateParams, $state, $uibModal, chart) {
            //         $uibModal.open({
            //             templateUrl: 'app/entities/allergies/allergies-dialog.html',
            //             controller: 'AllergiesDialogController',
            //             controllerAs: 'vm',
            //             backdrop: 'static',
            //             size: 'lg',
            //             resolve: {
            //                 allergies: function () {
            //                     return {
            //                         allergen: null,
            //                         allergenType: null,
            //                         reaction: null,
            //                         treatment: null,
            //                         status: 'ACT',
            //                         id: null
            //                     };
            //                 },
            //                 chart: chart
            //             }
            //         }).result.then(function () {
            //             $state.go('patient', null, {reload: 'patient'});
            //         }, function () {
            //             $state.go('patient');
            //         });
            //     }]
            // })
            // .state('allergies-edit', {
            //     parent: 'patient',
            //     url: '/{aid}/aedit',
            //     data: {
            //         authorities: ['ROLE_USER']
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', 'chart', function ($stateParams, $state, $uibModal, chart) {
            //         $uibModal.open({
            //             templateUrl: 'app/entities/allergies/allergies-dialog.html',
            //             controller: 'AllergiesDialogController',
            //             controllerAs: 'vm',
            //             backdrop: 'static',
            //             size: 'lg',
            //             resolve: {
            //                 allergies: ['Allergies', function (Allergies) {
            //                     return Allergies.get({id: $stateParams.aid}).$promise;
            //                 }],
            //                 chart: chart
            //             }
            //         }).result.then(function () {
            //             $state.go('patient', null, {reload: 'patient'});
            //         }, function () {
            //             $state.go('patient');
            //         });
            //     }]
            // })
            .state('allergies-delete', {
                parent: 'patient',
                url: '/{aid}/adelete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/allergies/allergies-delete-dialog.html',
                        controller: 'AllergiesDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Allergies', function (Allergies) {
                                return Allergies.get({id: $stateParams.aid}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('patient', null, {reload: 'patient'});
                    }, function () {
                        $state.go('patient');
                    });
                }]
            });
    }

})();
