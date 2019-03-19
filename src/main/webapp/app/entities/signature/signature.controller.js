(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('SignatureController', SignatureController);

    SignatureController.$inject = ['$scope', '$state', 'DataUtils', 'Signature', 'SignatureSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function SignatureController ($scope, $state, DataUtils, Signature, SignatureSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Signatures';
        vm.entityClassHumanized = 'Signature';
        vm.entityStateName = 'signature';
        
        vm.signatures = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Signature.query(function(result) {
                  vm.signatures = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.signatures, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
        .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $compile(nRow)($scope);
        });

        vm.dtColumns = [
              DTColumnBuilder.newColumn('signature').withTitle('Signature'),
              DTColumnBuilder.newColumn('ip').withTitle('Ip'),
              DTColumnBuilder.newColumn('date').withTitle('Date'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Signature.query(function(result) {
                vm.signatures = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['all']);
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
