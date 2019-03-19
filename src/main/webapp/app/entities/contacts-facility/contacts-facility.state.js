(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('contacts-facility', {
                parent: 'entity',
                url: '/contacts-facility',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                    pageTitle: 'ContactsFacilities'
                },
                ncyBreadcrumb: {
                  label: 'Contacts Facility'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/contacts-facility/contacts-facilities.html',
                        controller: 'ContactsFacilityController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            })
            .state('contacts-facility-detail', {
                parent: 'entity',
                url: '/contacts-facility/{id}',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                    pageTitle: 'ContactsFacility'
                },
                ncyBreadcrumb: {
                    label: 'New/Edit',
                    parent: 'contacts-facility'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/contacts-facility/contacts-facility-detail.html',
                        controller: 'ContactsFacilityDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ContactsFacility', function($stateParams, ContactsFacility) {
                        return ContactsFacility.get({id : $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'contacts-facility',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('contacts-facility-detail.edit', {
                parent: 'contacts-facility-detail',
                url: '/detail/edit',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/contacts-facility/contacts-facility-dialog.html',
                        controller: 'ContactsFacilityDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['ContactsFacility', function(ContactsFacility) {
                                return ContactsFacility.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('^', {}, { reload: false });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })

            .state('contacts-facility.edit', {
                parent: 'contacts-facility',
                url: '/{id}/edit',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                ncyBreadcrumb: {
                    label: 'Edit',
                    parent: 'contacts-facility'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/contacts-facility/contacts-facility-edit.html',
                        controller: 'ContactsFacilityEditController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ContactsFacility', function($stateParams, ContactsFacility) {
                        return ContactsFacility.get({id : $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'contacts-facility',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('contacts-facility.delete', {
                parent: 'contacts-facility',
                url: '/{id}/delete',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/contacts-facility/contacts-facility-delete-dialog.html',
                        controller: 'ContactsFacilityDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['ContactsFacility', function(ContactsFacility) {
                                return ContactsFacility.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('contacts-facility', null, { reload: 'contacts-facility' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('contacts-facility.new', {
                parent: 'contacts-facility',
                url: '/new',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                ncyBreadcrumb: {
                    label: 'New',
                    parent: 'contacts-facility'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/contacts-facility/contacts-facilities-new.html',
                        controller: 'ContactsFacilityNewController',
                        controllerAs: 'vm'
                    }
                }
            });
    }

})();
