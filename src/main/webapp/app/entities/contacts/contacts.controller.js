(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ContactsController', ContactsController);

    ContactsController.$inject = ['$compile', '$scope', '$stateParams', 'Contacts', 'ContactsSearch', '$q', 'DTColumnBuilder',
        'DTOptionsBuilder', '$uibModal', '$filter'];

    function ContactsController($compile, $scope, $stateParams, Contacts, ContactsSearch, $q, DTColumnBuilder,
                                DTOptionsBuilder, $uibModal, $filter) {
        var vm = this;

        vm.contacts = [];
        vm.search = search;
        vm.dtInstance = {};

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                var must = [];
                must.push({term: {"chartId": $stateParams.id}});
                must.push({term: {"delStatus": false}});

                ContactsSearch.query({
                    query: {
                        bool: {
                            must: must
                        }
                    }
                }, function (result) {
                    vm.contacts = result;
                    $scope.$emit('bluebookApp:contactsCount', {
                        total: result.length
                    });
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.contacts, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('aaSorting', []).withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('fullName').withTitle('Full Name'),
            DTColumnBuilder.newColumn(null).withTitle('Contact type').renderWith(function (data, type, full) {
                return data.typePatientContactTypes ? data.typePatientContactTypes.name : 'Empty';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Relationship').renderWith(function (data, type, full) {
                return data.typePatientContactsRelationship ? data.typePatientContactsRelationship.name : 'Empty';
            }),
            DTColumnBuilder.newColumn('phone').withTitle('Phone').renderWith(function (data, type, full) {
                return data ? data : 'Empty';
            }),
            DTColumnBuilder.newColumn('altPhone').withTitle('Alt Phone').renderWith(function (data, type, full) {
                return data ? data : 'Empty';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta) {
            return '<a class="btn-sm btn-primary" ng-click="vm.saveContact(' + data.id + ')">' +
                '<i class="fa fa-edit"></i></a> ' +
                ' <a class="btn-sm btn-danger" ng-click="vm.deleteContact(' + data.id + ')">' +
                '<i class="fa fa-trash"></i></a> ';
        }

        vm.saveContact = function (id) {
            var entity = null;
            //Edit the entity
            if (id) {
                entity = Contacts.get({id: id}).$promise;
            } else {
                entity = {
                    fullName: null,
                    contactType: null,
                    relationship: null,
                    phone: null,
                    altPhone: null,
                    email: null,
                    address: null,
                    notes: null,
                    status: 'ACT',
                    addressTwo: null,
                    city: null,
                    state: null,
                    zip: null,
                    id: null,
                    chartId: $stateParams.id
                };
            }

            $uibModal.open({
                templateUrl: 'app/entities/contacts/contacts-dialog.html',
                controller: 'ContactsDialogController',
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

        vm.deleteContact = function (id) {
            $uibModal.open({
                templateUrl: 'app/entities/contacts/contacts-delete-dialog.html',
                controller: 'ContactsDeleteController',
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
