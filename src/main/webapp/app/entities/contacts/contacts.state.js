(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contacts-detail', {
            parent: 'entity',
            url: '/contacts/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Contacts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contacts/contacts-detail.html',
                    controller: 'ContactsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Contacts', function($stateParams, Contacts) {
                    return Contacts.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'contacts',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('contacts-detail.edit', {
            parent: 'contacts-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacts/contacts-dialog.html',
                    controller: 'ContactsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Contacts', function(Contacts) {
                            return Contacts.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        // .state('contacts-new', {
        //     parent: 'patient',
        //     url: '/cnew',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', 'chart', function($stateParams, $state, $uibModal, chart) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/contacts/contacts-dialog.html',
        //             controller: 'ContactsDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         fullName: null,
        //                         contactType: null,
        //                         relationship: null,
        //                         phone: null,
        //                         altPhone: null,
        //                         email: null,
        //                         address: null,
        //                         notes: null,
        //                         status: 'ACT',
        //                         addressTwo: null,
        //                         city: null,
        //                         state: null,
        //                         zip: null,
        //                         id: null,
        //                     };
        //                 },
        //                 chart: chart
        //             }
        //         }).result.then(function() {
        //             $state.go('patient', null, { reload: 'patient'});
        //         }, function() {
        //             $state.go('patient');
        //         });
        //     }]
        // })
        // .state('contacts-edit', {
        //     parent: 'patient',
        //     url: '/{cid}/cedit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', 'chart', function($stateParams, $state, $uibModal, chart) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/contacts/contacts-dialog.html',
        //             controller: 'ContactsDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['Contacts', function(Contacts) {
        //                     return Contacts.get({id : $stateParams.cid}).$promise;
        //                 }],
        //                 chart: chart
        //             }
        //         }).result.then(function() {
        //             $state.go('patient', null, { reload: 'patient'});
        //         }, function() {
        //             $state.go('patient');
        //         });
        //     }]
        // })
        .state('contacts-delete', {
            parent: 'patient',
            url: '/{cid}/cdelete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacts/contacts-delete-dialog.html',
                    controller: 'ContactsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Contacts', function(Contacts) {
                            return Contacts.get({id : $stateParams.cid}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient', null, { reload: 'patient'});
                }, function() {
                    $state.go('patient');
                });
            }]
        });
    }
})();
