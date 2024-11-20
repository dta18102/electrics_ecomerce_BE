-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 22, 2024 lúc 10:56 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `medimateserver`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `id_role` int(11) NOT NULL DEFAULT 2,
  `phone` text DEFAULT NULL,
  `email` text DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` text DEFAULT NULL,
  `rank` text NOT NULL DEFAULT 'Đồng',
  `point` int(11) NOT NULL DEFAULT 0,
  `birthday` date DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `image` text DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `id_role`, `phone`, `email`, `password`, `username`, `rank`, `point`, `birthday`, `gender`, `image`, `status`) VALUES
(12, 2, '0963012812', '', '1', '', 'Đồng', 11500, '2024-04-03', 1, 'https://wac-cdn.atlassian.com/dam/jcr:ba03a215-2f45-40f5-8540-b2015223c918/Max-R_Headshot%20(1).jpg?cdnVersion=1539', 1),
(14, 1, '019292', 'abc@gmail.com', '1', '', 'Đồng', 0, NULL, NULL, NULL, 1),
(16, 1, '', 'xyz@gmail.com', '1', '', 'Đồng', 0, NULL, NULL, NULL, 1),
(18, 1, '01929223', '4', '1', '', 'Đồng', 0, NULL, NULL, NULL, 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`) USING HASH,
  ADD UNIQUE KEY `phone` (`phone`) USING HASH,
  ADD KEY `fk_user_role` (`id_role`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_role` FOREIGN KEY (`id_role`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
