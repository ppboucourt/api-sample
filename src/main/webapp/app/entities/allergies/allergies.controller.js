(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('AllergiesController', AllergiesController);

    AllergiesController.$inject = ['$compile', '$scope', '$stateParams', 'Allergies', 'AllergiesSearch', '$q',
        'DTColumnBuilder', 'DTOptionsBuilder', '$uibModal', '$filter'];

    function AllergiesController($compile, $scope, $stateParams, Allergies, AllergiesSearch, $q, DTColumnBuilder,
                                 DTOptionsBuilder, $uibModal, $filter) {
        var vm = this;

        vm.allergies = [];
        vm.search = search;
        vm.dtInstance = {};

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                var must = [];
                must.push({term: {"chartId": $stateParams.id}});
                must.push({term: {"delStatus": false}});

                AllergiesSearch.query({
                    query: {
                        bool: {
                            must: must
                        }
                    }
                }, function (result) {
                    vm.allergies = result;
                    $scope.$emit('bluebookApp:allergiesCount', {
                        total: result.length
                    });
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.allergies, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withOption('aaSorting', [])
            .withDOM('tip')
            .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('allergen').withTitle('Allergen'),
            DTColumnBuilder.newColumn('allergenType').withTitle('Allergen Type'),
            DTColumnBuilder.newColumn('reaction').withTitle('Reaction').renderWith(function (data, type, full) {
                return data ? data : 'Empty';
            }),
            DTColumnBuilder.newColumn('treatment').withTitle('Treatment').renderWith(function (data, type, full) {
                return data ? data : 'Empty';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta) {
            return '<a class="btn-sm btn-primary" ng-click="vm.saveAllergy(' + data.id + ')">' +
                '<i class="fa fa-edit"></i></a> ' +
                ' <a class="btn-sm btn-danger" ng-click="vm.deleteAllergy(' + data.id + ')">' +
                '<i class="fa fa-trash"></i></a> ';
        }

        vm.saveAllergy = function (id) {
            var entity = null;
            //Edit the entity
            if (id) {
                entity = Allergies.get({id: id}).$promise;
            } else {
                entity = {
                    allergen: null,
                    allergenType: null,
                    reaction: null,
                    treatment: null,
                    status: 'ACT',
                    id: null,
                    chartId: $stateParams.id
                };
            }

            $uibModal.open({
                templateUrl: 'app/entities/allergies/allergies-dialog.html',
                controller: 'AllergiesDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function () {
                vm.search();
            }, function (error) {
                console.log("error", error);
                //Error handling
            });
        }

        vm.deleteAllergy = function (id) {
            $uibModal.open({
                templateUrl: 'app/entities/allergies/allergies-delete-dialog.html',
                controller: 'AllergiesDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: {
                        id: id
                    }
                }
            }).result.then(function () {
                vm.search();
            }, function () {
                //Error
            });
        }


        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
