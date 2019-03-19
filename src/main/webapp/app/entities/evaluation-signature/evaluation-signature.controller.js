(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationSignatureController', EvaluationSignatureController);

    EvaluationSignatureController.$inject = ['$scope', '$state', 'EvaluationSignature', 'EvaluationSignatureSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function EvaluationSignatureController ($scope, $state, EvaluationSignature, EvaluationSignatureSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Evaluation Signatures';
        vm.entityClassHumanized = 'Evaluation Signature';
        vm.entityStateName = 'evaluation-signature';
        
        vm.evaluationSignatures = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  EvaluationSignature.query(function(result) {
                  vm.evaluationSignatures = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.evaluationSignatures, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
        .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $compile(nRow)($scope);
        });

        vm.dtColumns = [
              DTColumnBuilder.newColumn('role').withTitle('Role'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            EvaluationSignature.query(function(result) {
                vm.evaluationSignatures = result;
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
