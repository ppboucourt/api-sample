(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('facility', {
            parent: 'entity',
            url: '/facility',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                pageTitle: 'Facilities'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility/facilities.html',
                    controller: 'FacilityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('facility-detail', {
            parent: 'entity',
            url: '/facility/{id}',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                pageTitle: 'Facility'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility/facility-detail.html',
                    controller: 'FacilityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Facility', function($stateParams, Facility) {
                    return Facility.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'facility',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('facility-detail.edit', {
            parent: 'facility-detail',
            url: '/detail/edit',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility/facility-dialog.html',
                    controller: 'FacilityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Facility', function(Facility) {
                            return Facility.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facility.new', {
            parent: 'facility',
            url: '/new',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility/facility-dialog.html',
                    controller: 'FacilityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                street: null,
                                street_two: null,
                                city: null,
                                state: null,
                                zip: null,
                                county: null,
                                phone: null,
                                fax: null,
                                website: null,
                                logo: null,
                                logoContentType: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('facility', null, { reload: 'facility' });
                }, function() {
                    $state.go('facility');
                });
            }]
        })
        .state('facility.edit', {
            parent: 'facility',
            url: '/{id}/edit',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility/facility-dialog.html',
                    controller: 'FacilityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Facility', function(Facility) {
                            return Facility.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility', null, { reload: 'facility' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facility.delete', {
            parent: 'facility',
            url: '/{id}/delete',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility/facility-delete-dialog.html',
                    controller: 'FacilityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Facility', function(Facility) {
                            return Facility.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility', null, { reload: 'facility' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
