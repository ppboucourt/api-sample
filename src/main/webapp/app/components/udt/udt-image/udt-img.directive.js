(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .directive('udtImage', Image);

    Image.$inject = ['$uibModal', '$state', 'Chart'];

    function Image($uibModal, $state, Chart) {

        var directive = {
            restrict: 'E',
            templateUrl: 'app/components/udt/udt-image/udt-img.html',
            scope: {//all this scope value defined, are attr for the directive. Can be used like is explained below
                image: '=',//modal field for the image value
                typeImage: '=',//modal field form the image type
                size: '@',//size for the modal, can be: sm or lg[e.g: modalSize="sm"]. This attr modified the size of the modal
                class: '@',//form for the image component. Can be square or circle[e.g: class="img-circle/img-square"]
                service: '=',
                ngModel: '='//modal object(entity) to resolve
            },
            link: linkFunc
        };

        return directive;

        /* private helper methods*/

        function linkFunc($scope, element) {
            $scope.openModal = function (element) {
                $uibModal.open({
                    templateUrl: 'app/components/udt/udt-image/udt-img-dialog.html',
                    controller: 'ImageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: $scope.size,
                    resolve: {
                        image: function () {
                            return $scope.image;
                        },
                        typeImage: function () {
                            return $scope.typeImage;
                        }
                    }
                }).result.then(function (result) {}, function (result) {
                    if(result){
                        updateValue(result);
                    }
                });
            };

            function updateValue(value) {
                $scope.image = value.picture;
                $scope.typeImage = value.pictureContentType;

                switch ($scope.service) {
                    case 'Chart': {
                        $scope.ngModel.picture = value.picture;
                        $scope.ngModel.pictureContentType = value.pictureContentType;


                        var picture = $scope.ngModel.pictureRef ? $scope.ngModel.pictureRef: {};
                        picture.picture = value.picture;
                        picture.pictureContentType = value.pictureContentType;
                        picture.ownerId = $scope.ngModel.id;

                        $scope.ngModel.pictureRef = picture;

                        Chart.updatePictureVO(picture, onSaveSuccess, onSaveError);
                        break;
                    }
                    case 'Patient': {
                        return Patient;
                        break;
                    }
                }
            }

            var onSaveSuccess = function (result) {
                console.log(result);
            };

            var onSaveError = function (error) {
                console.log("Error:" + error);
            };
        }
    }
})();
